#!/usr/bin/env node
import { Server } from "@modelcontextprotocol/sdk/server/index.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import {
  CallToolRequestSchema,
  ListToolsRequestSchema,
  Tool,
} from "@modelcontextprotocol/sdk/types.js";
import { exec } from "child_process";
import { promisify } from "util";

const execAsync = promisify(exec);

// Configuration - Load from environment variables
const JIRA_CONFIG = {
  baseUrl: process.env.JIRA_BASE_URL || "https://your-domain.atlassian.net",
  email: process.env.JIRA_EMAIL || "your-email@example.com",
  apiToken: process.env.JIRA_API_TOKEN || "",
  projectKey: process.env.JIRA_PROJECT_KEY || "KAN",
};

// Validate configuration
if (!process.env.JIRA_API_TOKEN) {
  console.error("ERROR: JIRA_API_TOKEN environment variable is required");
  console.error("Please set your Jira API token in the environment variables");
  process.exit(1);
}

// Base64 encode credentials for Basic Auth
const authHeader = Buffer.from(
  `${JIRA_CONFIG.email}:${JIRA_CONFIG.apiToken}`
).toString("base64");

// Helper function to make Jira API calls
async function callJiraApi(
  endpoint: string,
  method: string = "GET",
  body?: any
): Promise<any> {
  const url = `${JIRA_CONFIG.baseUrl}/rest/api/3${endpoint}`;

  try {
    const response = await fetch(url, {
      method,
      headers: {
        Authorization: `Basic ${authHeader}`,
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: body ? JSON.stringify(body) : undefined,
    });

    const text = await response.text();

    if (!response.ok) {
      throw new Error(
        `Jira API error: ${response.status} ${response.statusText}\n${text}`
      );
    }

    return text ? JSON.parse(text) : {};
  } catch (error) {
    console.error(`Error calling Jira API: ${error}`);
    throw error;
  }
}

// Helper to get current git branch
async function getCurrentBranch(): Promise<string> {
  try {
    const { stdout } = await execAsync("git rev-parse --abbrev-ref HEAD");
    return stdout.trim();
  } catch (error) {
    return "";
  }
}

// Helper to create and checkout a new branch
async function createBranch(branchName: string): Promise<void> {
  try {
    await execAsync(`git checkout -b ${branchName}`);
  } catch (error: any) {
    throw new Error(`Failed to create branch: ${error.message}`);
  }
}

// Helper to extract ticket ID from text
function extractTicketId(text: string): string | null {
  const match = text.match(/(?:KAN-)?(\d+)/);
  if (match) {
    return `KAN-${match[1]}`;
  }
  return null;
}

// Create MCP server
const server = new Server(
  {
    name: "jira-integration",
    version: "1.0.0",
  },
  {
    capabilities: {
      tools: {},
    },
  }
);

// Define available tools
const tools: Tool[] = [
  {
    name: "start_work",
    description: "Start work on a Jira ticket - creates branch and moves ticket to In Progress",
    inputSchema: {
      type: "object",
      properties: {
        ticket: {
          type: "string",
          description: "Ticket number (e.g., '123' or 'KAN-123')",
        },
      },
      required: ["ticket"],
    },
  },
  {
    name: "list_my_tickets",
    description: "List all Jira tickets assigned to me",
    inputSchema: {
      type: "object",
      properties: {
        status: {
          type: "string",
          description: "Optional status filter (e.g., 'In Progress', 'To Do')",
        },
      },
    },
  },
  {
    name: "get_ticket_info",
    description: "Get detailed information about a specific Jira ticket",
    inputSchema: {
      type: "object",
      properties: {
        ticket: {
          type: "string",
          description: "Ticket number (e.g., '456' or 'KAN-456')",
        },
      },
      required: ["ticket"],
    },
  },
  {
    name: "create_ticket",
    description: "Create a new Jira ticket",
    inputSchema: {
      type: "object",
      properties: {
        summary: {
          type: "string",
          description: "Ticket summary/title",
        },
        description: {
          type: "string",
          description: "Ticket description",
        },
        type: {
          type: "string",
          description: "Issue type (Bug, Task, Story)",
          enum: ["Bug", "Task", "Story"],
        },
      },
      required: ["summary", "type"],
    },
  },
  {
    name: "add_comment",
    description: "Add a comment to a Jira ticket",
    inputSchema: {
      type: "object",
      properties: {
        ticket: {
          type: "string",
          description: "Ticket number (e.g., '789' or 'KAN-789')",
        },
        comment: {
          type: "string",
          description: "Comment text to add",
        },
      },
      required: ["ticket", "comment"],
    },
  },
];

// Handle list tools request
server.setRequestHandler(ListToolsRequestSchema, async () => {
  return {
    tools,
  };
});

// Handle tool calls
server.setRequestHandler(CallToolRequestSchema, async (request) => {
  const { name, arguments: args } = request.params;

  if (!args) {
    throw new Error("No arguments provided");
  }

  try {
    switch (name) {
      case "start_work": {
        const ticketId = extractTicketId(args.ticket as string);
        if (!ticketId) {
          throw new Error("Invalid ticket ID format");
        }

        // Get ticket details
        const ticket = await callJiraApi(`/issue/${ticketId}`);
        const summary = ticket.fields.summary
          .toLowerCase()
          .replace(/[^a-z0-9]+/g, "-")
          .replace(/(^-|-$)/g, "")
          .substring(0, 50);

        const branchName = `feature/${ticketId}-${summary}`;

        // Create and checkout branch
        await createBranch(branchName);

        // Get available transitions
        const transitionsResponse = await callJiraApi(
          `/issue/${ticketId}/transitions`
        );

        // Find "In Progress" transition
        const inProgressTransition = transitionsResponse.transitions.find(
          (t: any) =>
            t.name === "In Progress" ||
            t.name === "Start Progress" ||
            t.name === "In Development"
        );

        if (inProgressTransition) {
          // Move ticket to In Progress
          await callJiraApi(`/issue/${ticketId}/transitions`, "POST", {
            transition: { id: inProgressTransition.id },
          });

          // Add comment
          await callJiraApi(`/issue/${ticketId}/comment`, "POST", {
            body: {
              type: "doc",
              version: 1,
              content: [
                {
                  type: "paragraph",
                  content: [
                    {
                      type: "text",
                      text: `🚀 Started work on this ticket. Branch created: ${branchName}`,
                    },
                  ],
                },
              ],
            },
          });

          return {
            content: [
              {
                type: "text",
                text: `✅ Successfully started work on ${ticketId}
- Created branch: ${branchName}
- Moved ticket to In Progress
- Ticket: ${ticket.fields.summary}
- URL: ${JIRA_CONFIG.baseUrl}/browse/${ticketId}`,
              },
            ],
          };
        } else {
          return {
            content: [
              {
                type: "text",
                text: `✅ Branch created: ${branchName}
⚠️ Could not move ticket to In Progress (transition not available)
- Ticket: ${ticket.fields.summary}
- URL: ${JIRA_CONFIG.baseUrl}/browse/${ticketId}`,
              },
            ],
          };
        }
      }

      case "list_my_tickets": {
        const jql = (args as any).status
          ? `assignee = currentUser() AND status = "${(args as any).status}" ORDER BY updated DESC`
          : `assignee = currentUser() ORDER BY updated DESC`;

        const response = await callJiraApi(
          `/search?jql=${encodeURIComponent(jql)}&maxResults=20`
        );

        if (!response.issues || response.issues.length === 0) {
          return {
            content: [
              {
                type: "text",
                text: "No tickets found assigned to you.",
              },
            ],
          };
        }

        const ticketList = response.issues
          .map(
            (issue: any) =>
              `• ${issue.key}: ${issue.fields.summary}
  Status: ${issue.fields.status.name}
  Type: ${issue.fields.issuetype.name}
  Priority: ${issue.fields.priority?.name || "None"}
  URL: ${JIRA_CONFIG.baseUrl}/browse/${issue.key}`
          )
          .join("\n\n");

        return {
          content: [
            {
              type: "text",
              text: `📋 Your Jira Tickets (${response.issues.length}):\n\n${ticketList}`,
            },
          ],
        };
      }

      case "get_ticket_info": {
        const ticketId = extractTicketId((args as any).ticket as string);
        if (!ticketId) {
          throw new Error("Invalid ticket ID format");
        }

        const ticket = await callJiraApi(`/issue/${ticketId}`);
        const fields = ticket.fields;

        // Get comments
        const comments = await callJiraApi(`/issue/${ticketId}/comments`);
        const recentComments = comments.comments
          .slice(-3)
          .map(
            (c: any) =>
              `  - ${new Date(c.created).toLocaleDateString()}: ${
                c.body.content?.[0]?.content?.[0]?.text || "No text"
              }`
          )
          .join("\n");

        const info = `📌 ${ticketId}: ${fields.summary}

Status: ${fields.status.name}
Type: ${fields.issuetype.name}
Priority: ${fields.priority?.name || "None"}
Assignee: ${fields.assignee?.displayName || "Unassigned"}
Reporter: ${fields.reporter?.displayName || "Unknown"}
Created: ${new Date(fields.created).toLocaleDateString()}
Updated: ${new Date(fields.updated).toLocaleDateString()}

Description:
${fields.description?.content?.[0]?.content?.[0]?.text || "No description"}

Recent Comments:
${recentComments || "  No comments"}

URL: ${JIRA_CONFIG.baseUrl}/browse/${ticketId}`;

        return {
          content: [
            {
              type: "text",
              text: info,
            },
          ],
        };
      }

      case "create_ticket": {
        const newTicket = await callJiraApi("/issue", "POST", {
          fields: {
            project: {
              key: JIRA_CONFIG.projectKey,
            },
            summary: (args as any).summary,
            description: {
              type: "doc",
              version: 1,
              content: [
                {
                  type: "paragraph",
                  content: [
                    {
                      type: "text",
                      text: (args as any).description || "",
                    },
                  ],
                },
              ],
            },
            issuetype: {
              name: (args as any).type,
            },
          },
        });

        return {
          content: [
            {
              type: "text",
              text: `✅ Created ticket ${newTicket.key}
Summary: ${(args as any).summary}
Type: ${(args as any).type}
URL: ${JIRA_CONFIG.baseUrl}/browse/${newTicket.key}`,
            },
          ],
        };
      }

      case "add_comment": {
        const ticketId = extractTicketId((args as any).ticket as string);
        if (!ticketId) {
          throw new Error("Invalid ticket ID format");
        }

        await callJiraApi(`/issue/${ticketId}/comment`, "POST", {
          body: {
            type: "doc",
            version: 1,
            content: [
              {
                type: "paragraph",
                content: [
                  {
                    type: "text",
                    text: (args as any).comment,
                  },
                ],
              },
            ],
          },
        });

        return {
          content: [
            {
              type: "text",
              text: `✅ Comment added to ${ticketId}`,
            },
          ],
        };
      }

      default:
        throw new Error(`Unknown tool: ${name}`);
    }
  } catch (error: any) {
    return {
      content: [
        {
          type: "text",
          text: `❌ Error: ${error.message}`,
        },
      ],
    };
  }
});

// Start the server
async function main() {
  const transport = new StdioServerTransport();
  await server.connect(transport);
  console.error("Jira MCP Server running...");
}

main().catch((error) => {
  console.error("Server error:", error);
  process.exit(1);
});