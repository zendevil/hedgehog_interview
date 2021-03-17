# hedgehog-gauntlet-be
Back-end coding assessment for potential Hedgehog hires. You may use any tools or languages you are comfortable with. The challenge is deliberately generic to see how you conceptualize the problem statement and the solution space.

# The Assesment

- Configure a master server with n workers determined at run-time
- Connect to the [Coinbase Pro streaming API](https://docs.pro.coinbase.com/#subscribe)
- Support connections from multiple clients using WebSockets
- Create a REST subscription endpoint that clients can hit to start and stop streaming data on a currency pair
- Forward streaming data from the Coinbase Pro streaming API to any subscribed clients
- Use git to track your progress and leave commit messages as if this was your job

# Stretch Goals

- Implement the number of tests you feel is appropriate
- Containerize the solution for easy sharing and deployment
- Build a CI/CD pipeline in the tool of your choice
- Put together a front-end for easy client interaction
- Store a history of events in an in-memory store or database of your choice
- Create an auth solution that supports deny/allow for classes of users
