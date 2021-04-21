# hedgehog-gauntlet-be
Back-end coding assessment for potential Hedgehog hires. You may use any tools or languages you are comfortable with. The challenge is deliberately generic to see how you conceptualize the problem statement and the solution space.

# The Assesment
We do not expect you to spend more than 6 hours on this test, but you may work on it as much as you need or want while you have access. Please work to complete the requirements under **The Assessment** before you even consider the items under **Stretch Goals**. Understand that we are aware of your experience and expertise, so we know if this is outside your wheelhouse. We want to see any sort of progress you can make toward a solution.
- Configure a load balancer or server cluster with n workers determined at run-time using an environment variable `WEB_CONCURRENCY`
- Connect to the [Coinbase Pro streaming API](https://docs.pro.coinbase.com/#subscribe)
- Support connections from multiple clients using WebSockets
- Create a REST subscription endpoint at /api/v1/subscribe/:pair that clients can hit to start and stop streaming data on a currency pair
- Forward streaming data from the Coinbase Pro streaming API to any subscribed clients
- Use git to track your progress on a branch titled with candidate followed by a dash, and your full name joined by underscores (ie `candidate-full_name`), and leave commit messages as if this was your job
- Your first commit should include an integer hour estimate for the time to completion

# Stretch Goals (NOT REQUIRED!)
Want to show us something else? Feel free to show-off what you can do. None of these are required at all, but here are a few sample ideas:
- Implement the number of tests you feel is appropriate
- Containerize the solution for easy sharing and deployment
- Build a CI/CD pipeline in the tool of your choice
- Put together a front-end for easy client interaction
- Store a history of events in an in-memory store or database of your choice
- Create an auth solution that supports deny/allow for classes of users
- Surprise us with new functionality, more developer tooling, or arcane spells
