![Java CI with Maven](https://github.com/link-intersystems/clean-architecture-example/workflows/Java%20CI%20with%20Maven/badge.svg) 

This repository contains an example implementation of the clean architecture, by implementing a car rental application.

# Notes about the source code

When you take a look at the source code you should keep in mind that you see one solution
for every particular technical problem that arises when implementing the clean architecture.
For a lot of situations there are more than one way to solve them. Anyway, all solutions must
honor the dependency rules of the architecture.

If you would like to learn about the different ways, their pros and cons and the implementation
challenges, you should take my [udemy course](https://www.clean-code.guru) and/or take look at my [youtube channel](https://www.youtube.com/@cleancodeguru) (german language).

# car-rental


This car rental module implements basic car rental use cases:

1. make car offers
2. book car


# Implement more use cases

You might want to explore the clean architecture further by implementing more user stories.
Here is a list of user story proposals.

1. As a sales manager I would like to define multiple rental rates with
a different, but never overlapping, validity period, so that rates can vary over the time.