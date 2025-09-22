#!/bin/bash

# Test script to verify all Jira transitions are working
# Usage: ./test-jira-transitions.sh KAN-1

TICKET_ID="${1:-KAN-1}"
AUTH="YWJkdWxyYWhtYW4uc2FpZC5iYXNzdW9ueUBnbWFpbC5jb206QVRBVFQzeEZmR0YwT0VvazRMZG1wYkp5V3dwNkJhRVZlc2ZsWmVRcl85TzdOYmxndEhOT2FOWVEtSkdfcnhmeEZ2YmlyQXRqamxKSGswWl9VVnMxVnd6VnBsQ1dEUUdOaGNJZkxpVzdYUU5jSk1qNHBpNlJKR1JqaFEwamxwd1ZoSU91S09BQndzdFF5cnYzbEpiZWgxZVA0WHRsQW45WFE5RnVuNXI2MFUwZU1WaGwwOElDVTRVPTNFMDExMTVE"

echo "🧪 Testing Jira transitions for ticket: $TICKET_ID"
echo "================================================"

# Get current status
echo -e "\n📊 Current Status:"
CURRENT=$(curl -s -X GET \
  -H "Authorization: Basic $AUTH" \
  -H "Accept: application/json" \
  "https://abdo-said.atlassian.net/rest/api/3/issue/$TICKET_ID" | jq -r '.fields.status.name')

echo "   $CURRENT"

# Get all available transitions
echo -e "\n🔄 Available Transitions:"
TRANSITIONS=$(curl -s -X GET \
  -H "Authorization: Basic $AUTH" \
  -H "Accept: application/json" \
  "https://abdo-said.atlassian.net/rest/api/3/issue/$TICKET_ID/transitions")

echo "$TRANSITIONS" | jq -r '.transitions[] | "   - \(.name) (ID: \(.id))"'

# Test each transition scenario
echo -e "\n📝 Testing PR Scenarios:"
echo "================================================"

# Scenario 1: PR Opened (should go to IN REVIEW)
echo -e "\n1️⃣ PR Opened - Target: IN REVIEW"
TARGET_STATUSES=("IN REVIEW" "In Review" "IN PR" "In PR" "CODE REVIEW" "Code Review")
FOUND=false

for status in "${TARGET_STATUSES[@]}"; do
  ID=$(echo "$TRANSITIONS" | jq -r ".transitions[] | select(.name | ascii_downcase == \"$(echo "$status" | tr '[:upper:]' '[:lower:]')\") | .id")
  if [ ! -z "$ID" ] && [ "$ID" != "null" ]; then
    echo "   ✅ Can transition to: $(echo "$TRANSITIONS" | jq -r ".transitions[] | select(.id==\"$ID\") | .name")"
    FOUND=true
    break
  fi
done

if [ "$FOUND" = false ]; then
  echo "   ❌ No matching transition found"
fi

# Scenario 2: PR Merged (should go to QA READY)
echo -e "\n2️⃣ PR Merged - Target: QA READY"
TARGET_STATUSES=("QA READY" "QA" "Ready for QA" "TESTING" "Testing" "DONE" "Done")
FOUND=false

for status in "${TARGET_STATUSES[@]}"; do
  ID=$(echo "$TRANSITIONS" | jq -r ".transitions[] | select(.name | ascii_downcase == \"$(echo "$status" | tr '[:upper:]' '[:lower:]')\") | .id")
  if [ ! -z "$ID" ] && [ "$ID" != "null" ]; then
    echo "   ✅ Can transition to: $(echo "$TRANSITIONS" | jq -r ".transitions[] | select(.id==\"$ID\") | .name")"
    FOUND=true
    break
  fi
done

if [ "$FOUND" = false ]; then
  echo "   ❌ No matching transition found"
fi

# Scenario 3: PR Closed without merge (should go to TO DO or IN PROGRESS)
echo -e "\n3️⃣ PR Closed - Target: TO DO or IN PROGRESS"
TARGET_STATUSES=("TO DO" "To Do" "TODO" "IN PROGRESS" "In Progress" "BACKLOG" "Backlog")
FOUND=false

for status in "${TARGET_STATUSES[@]}"; do
  ID=$(echo "$TRANSITIONS" | jq -r ".transitions[] | select(.name | ascii_downcase == \"$(echo "$status" | tr '[:upper:]' '[:lower:]')\") | .id")
  if [ ! -z "$ID" ] && [ "$ID" != "null" ]; then
    echo "   ✅ Can transition to: $(echo "$TRANSITIONS" | jq -r ".transitions[] | select(.id==\"$ID\") | .name")"
    FOUND=true
    break
  fi
done

if [ "$FOUND" = false ]; then
  echo "   ❌ No matching transition found"
fi

echo -e "\n================================================"
echo "✅ Test complete!"
echo ""
echo "📚 Quick Commands:"
echo "   Start work: node mcp-jira-server/start-work.js $TICKET_ID"
echo "   Move to review: node move-to-review.js $TICKET_ID"
echo "   Move to todo: node move-to-todo.js $TICKET_ID"
echo ""
echo "🔗 Jira URL: https://abdo-said.atlassian.net/browse/$TICKET_ID"