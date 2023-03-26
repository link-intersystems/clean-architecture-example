![Java CI with Maven](https://github.com/link-intersystems/clean-architecture-example/workflows/Java%20CI%20with%20Maven/badge.svg)

This repository contains an example implementation of the clean architecture based on a simple car rental application
which is complex enough to address common implementation issues.

Following the "screaming architecture" rule I tried to make the structure clear and clean. Therefore, you will find
domain-related terms at the top level of this git repository.

The components in this example use the *package by component* strategy that Simon Brown introduced in the clean
architecture
book - chapter 34, the missing chapter.

I also implemented a basic swing ui that you can use to try out the application. Since it
is a basic ui it neither have any convenience features nor it is very nice. Since the focus
of this example repository is on the clean architecture center: use cases, entities, repositories and testing.

# Notes about the source code

When you take a look at the source code you should keep in mind that you see one solution
for every particular technical problem that arises when implementing the clean architecture.
For a lot of situations there are more than one way to solve them. Anyway, all solutions must
honor the dependency rules of the architecture.

If you would like to learn about the different ways, their pros and cons and the implementation
challenges, you should take a look at my [youtube channel](https://www.youtube.com/@cleancodeguru) (german language).

# car-rental

This car rental module implements basic car rental use cases:

1. make car offers
2. book car

# Implement more use cases

You might want to explore the clean architecture further by implementing more user stories.
Here is a list of user story proposals.

1. As a sales manager I would like to define multiple rental rates with
   a different, but never overlapping, validity period, so that rates can vary over the time.
2. As a sales manager I would like to specify different stations where the customer can pick up cars.
3. As a customer I would like to get offers for stations in the city where I live (my address).