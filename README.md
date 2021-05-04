# Prit's message

You can run the server locally by downloading ``target/uberjar/hedgehog-interview.jar`` to your local machine and running `java -jar hedgehog-interview.jar`. The websocket is exposed on the port `8982` and the http server is exposed on `3000`.
Make sure that both your websocket connection and your http requests contain the header `client-id`. The value of `client-id` can be any string.
To start and stop the streaming for a client, you'd have to make the request with the same `client-id` that you created the websocket connection with (specified in the header).
I'm still working on launching multiple processes over a K8s cluster and deploying it on AWS EKS with a load balancer. 


# hedgehog-gauntlet-be
Back-end coding assessment for potential Hedgehog hires. You may use any tools or languages you are comfortable with. The challenge is deliberately generic to see how you conceptualize the problem statement and the solution space. That being said, if you have any questions or concerns, please let us know! We're happy to give more clarity.

# The Assessment
We do not expect you to spend more than 6 hours on this test, but you may work on it as much as you need or want while you have access. Please work to complete the requirements under **The Assessment** before you even consider the items under **Stretch Goals**. Understand that we are aware of your experience and expertise, so we know if this is outside your wheelhouse. We want to see any sort of progress you can make toward a solution.
- Connect to the [Coinbase Pro streaming API](https://docs.pro.coinbase.com/#subscribe)
- Allow multiple clients to connect to your service using WebSockets or Streams
- Create a REST subscription endpoint at `/api/v1/subscribe/:pair` that clients can hit to start and stop streaming data on a currency pair
- Forward streaming data from the Coinbase Pro streaming API to any subscribed clients
- Configure a load balancer or server cluster to accept and distribute client requests across multiple processes
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
