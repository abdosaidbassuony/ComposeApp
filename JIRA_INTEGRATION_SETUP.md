# Complete Jira-Git Integration Setup Guide

## 🚀 Quick Setup Commands (Copy & Paste)

### Step 1: Set up GitHub Secrets
First, add your Jira authentication to GitHub secrets:

1. Go to your GitHub repository: https://github.com/abdosaidbassuony/ComposeApp
2. Navigate to Settings → Secrets and variables → Actions
3. Click "New repository secret"
4. Add a secret named `JIRA_AUTH` with this value:
```
YWJkdWxyYWhtYW4uc2FpZC5iYXNzdW9ueUBnbWFpbC5jb206QVRBVFQzeEZmR0YwT0VvazRMZG1wYkp5V3dwNkJhRVZlc2ZsWmVRcl85TzdOYmxndEhOT2FOWVEtSkdfcnhmeFZ2YmlyQXRqamxKSGswWl9VVnMxVnd6VnBsQ1dEUUdOaGNJZkxpVzdYUU5jSk1qNHBpNlJKR1JqaFEwamxwd1ZoSU91S09BQndzdFF5cnYzLEpiZWgxZVA0WHRsQW45WFE5RnVuNXI2MFUwZU1WaGwwOElDVTRVPTNFMDExMTVE
```

### Step 2: Install MCP Server
Run these commands in your terminal:

```bash
# Navigate to MCP server directory
cd /Users/abdulrahmansaid/StudioProjects/ComposeApp/mcp-jira-server

# Install dependencies
npm install

# Build the TypeScript code
npm run build

# The server is now ready!
```

### Step 3: Configure Claude Desktop
Add the MCP server to Claude Desktop:

**On macOS:**
```bash
# Open Claude Desktop config
open ~/Library/Application\ Support/Claude/claude_desktop_config.json
```

**Add this to the config file:**
```json
{
  "mcpServers": {
    "jira-integration": {
      "command": "node",
      "args": ["/Users/abdulrahmansaid/StudioProjects/ComposeApp/mcp-jira-server/dist/index.js"],
      "env": {}
    }
  }
}
```

**Then restart Claude Desktop:**
```bash
# Kill Claude Desktop
pkill Claude

# Reopen Claude Desktop (it will restart with the new config)
```

### Step 4: Test Jira Connection
Test your Jira API connection with this curl command:

```bash
curl -X GET \
  -H "Authorization: Basic YWJkdWxyYWhtYW4uc2FpZC5iYXNzdW9ueUBnbWFpbC5jb206QVRBVFQzeEZmR0YwT0VvazRMZG1wYkp5V3dwNkJhRVZlc2ZsWmVRcl85TzdOYmxndEhOT2FOWVEtSkdfcnhmeFZ2YmlyQXRqamxKSGswWl9VVnMxVnd6VnBsQ1dEUUdOaGNJZkxpVzdYUU5jSk1qNHBpNlJKR1JqaFEwamxwd1ZoSU91S09BQndzdFF5cnYzLEpiZWgxZVA0WHRsQW45WFE5RnVuNXI2MFUwZU1WaGwwOElDVTRVPTNFMDExMTVE" \
  -H "Accept: application/json" \
  "https://abdo-said.atlassian.net/rest/api/3/myself" | jq .
```

You should see your user information if the connection is working.

## 📋 How to Use

### GitHub Actions (Automatic)
The workflow triggers automatically when you:
- **Open a PR** → Moves ticket to "In Review"
- **Merge a PR** → Moves ticket to "QA" or "Done"
- **Close PR without merging** → Moves ticket back to "To Do"

**Important:** Your branch must contain the ticket ID (e.g., `feature/KAN-123-add-login`)

### MCP Server Commands with Claude
After restarting Claude Desktop, you can say:

- **"Start work on ticket 123"** → Creates branch `feature/KAN-123-description` and moves ticket to "In Progress"
- **"List my tickets"** → Shows all your assigned Jira tickets
- **"Get info for ticket 456"** → Shows detailed ticket information
- **"Create a bug ticket for login issue"** → Creates a new Jira ticket
- **"Add comment to ticket 789: Fixed the issue"** → Adds a comment to the ticket

## 🧪 Testing the Integration

### Test GitHub Actions:
1. Create a test branch with a Jira ticket ID:
```bash
git checkout -b feature/KAN-1-test-integration
```

2. Make a small change and push:
```bash
echo "test" > test.txt
git add test.txt
git commit -m "Test Jira integration"
git push -u origin feature/KAN-1-test-integration
```

3. Open a PR on GitHub and watch the Actions tab

### Test MCP Server:
In Claude Desktop, try:
```
List my Jira tickets
```

## 🔧 Troubleshooting

### If GitHub Actions fail:
- Check that `JIRA_AUTH` secret is set correctly
- Ensure ticket ID is in branch name (e.g., KAN-123)
- Check Actions logs for specific errors

### If MCP Server doesn't work:
1. Check server is built:
```bash
ls /Users/abdulrahmansaid/StudioProjects/ComposeApp/mcp-jira-server/dist/index.js
```

2. Restart Claude Desktop after config changes

3. Check Claude Desktop logs:
```bash
tail -f ~/Library/Logs/Claude/mcp.log
```

### Common Issues:
- **"Transition not found"**: Your Jira workflow might use different status names. The integration tries multiple common names automatically.
- **"401 Unauthorized"**: Check your API token hasn't expired
- **"404 Not Found"**: Verify the ticket ID exists and project key is correct (KAN)

## 📊 Features

### GitHub Actions Features:
✅ Automatic status updates based on PR state
✅ Comments added to Jira with PR links
✅ Comments added to PR with Jira links
✅ Smart transition detection (finds correct status names)
✅ Error handling and fallbacks
✅ Support for feature/, bugfix/, hotfix/ branches

### MCP Server Features:
✅ Create branches with proper naming
✅ Move tickets through workflow
✅ List and filter assigned tickets
✅ Get detailed ticket information
✅ Create new tickets
✅ Add comments to tickets
✅ Full error handling

## 🔐 Security Notes

- API token is stored securely in GitHub Secrets
- Never commit the API token to your repository
- The base64 encoded auth string is safe to use in GitHub Actions
- MCP server runs locally, credentials stay on your machine

## 📝 Workflow States Supported

The integration automatically detects and uses these Jira statuses:
- **To Do** / Backlog / Open
- **In Progress** / In Development / Start Progress
- **In Review** / Code Review / In PR
- **QA** / Testing / Ready for QA
- **Done** / Resolved / Closed

## Need Help?

If you encounter issues:
1. Check the Actions tab in GitHub for workflow logs
2. Run the test curl command to verify API access
3. Ensure your Jira project uses standard workflow states

Your integration is now fully configured and ready to use! 🎉