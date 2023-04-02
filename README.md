![Java CI with Maven](https://github.com/link-intersystems/clean-architecture-example/workflows/Java%20CI%20with%20Maven/badge.svg)
[![Coverage Status](https://coveralls.io/repos/github/link-intersystems/clean-architecture-example/badge.svg?branch=main)](https://coveralls.io/github/link-intersystems/clean-architecture-example?branch=main)

# Clean Architecture in Pure Java

This repository contains an example implementation of the clean architecture in [pure Java](#as-pure-as-possible).
You can either use this example as a starting point for discussions about the clean architecture and its various
implementation details, or you can use it to learn more about the clean architecture.

![Clean Architecture by Uncle Bob (Robert C. Martin)](src/site/resources/CleanArchitecture.jpg)
[The Clean Architecture, Robert C. Martin, 13 August 2012](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

## Table Of Contents

- [Screaming Architecture](#screaming-architecture)
- [Package By Component](#package-by-component)
- [As Pure As Possible](#as-pure-as-possible)
- [Implementation Notes](#implementation-notes)
  - [Use Cases And Interactors](#use-cases-and-interactors)
  - [This Example Architecture](#this-example-architecture)
- [More Sources](#more-sources)
- [The Example Domain](#the-example-domain)
  - [Domain Modules](#domain-modules)
  - [Details](#details)
    - [User Interface](#user-interface)
    - [Database](#database)

## Screaming Architecture

Uncle Bob told us that good architecture is focused on the use cases and not on details like frameworks, tools and so on.
Following the "screaming architecture" rule I tried to focus on domain aspects. Therefore, you will
find domain-related terms at the top level of this project.

## Package By Component

All [domain modules](#domain-modules) in this project are structured using the *package by component* strategy as described by Simon Brown in the Clean
Architecture book - chapter 34, the missing chapter.

![Package By Component, Simon Brown, The Missing Chapter, Clean Architecture Book](src/site/resources/package-by-component.png)

The package by component strategy makes use of Java's default access modifier that only allows access from
classes within the same package. It would also be possible to use the Java module system or OSGI, but I wanted
to use the simplest solution that works.

If you would like to learn more about the other packaging strategies take a look at chapter 34, the missing chapter, in
the [Clean Architecture book](https://www.amazon.com/Clean-Architecture-Craftsmans-Software-Structure/dp/0134494164).

## As Pure As Possible

This example does not use a lot of frameworks and libraries. Especially not for the application bootstrap, 
the use cases or entities and the object-relational mapping. Thus, you will not find any Spring or Hibernate and
even no apache commons library.

Although I like frameworks and libraries as much as you probably do, I want to show how a complete application works
that is implemented in pure Java. You will probably be surprised that many frameworks and libraries can be omitted 
without significantly increasing the implementation effort. We should consider this before using a framework or library,
especially if we made the experience that framework updates sometimes make a lot of effort. Also license issues do not 
arise if we do not use them and a fix is easy to make. I don't want to say that frameworks and libraries are bad, 
but they come at a cost that we often forget. So if we use them we should know why we use them, and we should separate 
them from the core of our application to protect it. Most libraries I use in this example application are written by myself.

## Implementation Notes

When you browse the source code you should keep in mind that you see one solution for every particular technical problem
that arises when implementing the clean architecture. For a lot of situations there are more than one way to solve them.
Anyway, all solutions must honor the dependency rules of the architecture. Feel free to play with the architecture and
refactor any aspect you would like to change to see what is easy to change and what is not.

### Use Cases And Interactors

In the context of the clean architecture you will often see a diagram that uses the terms:

- use case interactor
- input and output boundary
- request/response models.

![Clean Architecture](src/site/resources/boundaries%20and%20layers.jpg)

These terms are a mix between the boundary-controller-entity (BCE) by Ivar Jacobson, the hexagonal architecture
by Alistair Cockburn and the clean architecture by Robert C. Martin.

In the BCE architecture you have a boundary, a controller and an entity, as the name implies. In the hexagonal architecture
you talk about input and output ports and the clean architecture uses the terms interactors, request and response models,
and entities.

#### This Example Architecture

In this example I use the term use case for the API instead of input boundary and interactor for the use case implementation.
Request and Response models are the input and output models of the use case.

![Example Architecture](src/site/resources/example-architecture.png)

In the UI presenters are used by a controller to present response models to the user and to transform user input to
request models. The controller is invoked by the UI as a result of an event. It then uses the presenter to create a request
model and invokes the use case.

In this point my architecture is different to the diagram I showed above, because it does not decouple a presenter from a
controller, and thus it doesn't need to pass an output boundary to a use case interactor. Therefore, my architecture
has a lot less complexity in this area, but at the cost that controllers are not separated from presenters. It's true
that I don't have the benefits of decoupling them, but I also don't have a lot of implementation issues that arise from
the decoupling. It's a tradeoff that I made in the UI/use case integration to reduce the complexity in that area. I made
that decision, because the focus of the example application is the use case part, how these classes are structured and
how different modules integrate.

Since this example should be used as a starting point for discussions, feel free to refactor the UI/use case integration part
and [start a discussion](https://github.com/link-intersystems/clean-architecture-example/discussions).

## More Sources

If you would like to learn more about clean code and the clean architecture,
you should take a look at my [youtube channel - clean-code.guru (german)](https://www.youtube.com/@cleancodeguru).

I also would like to recommend the [cleancoders.com](https://cleancoders.com) website, where you can find a lot of
videos about clean code. Most of them provided by Robert "Uncle Bob" Martin and Micah Martin.

## The Example Domain

For this clean architecture example I chose a car rental application which is complex enough to address common implementation issues, but simple enough 
to stay focused on the architecture and not the domain.

### Domain Modules

The application is made of 2 domain modules. Follow the links of the modules for details about each one.

- car-booking
   
  The car-booking module contains the use cases a customer executes, like find car offers and book a car.  
- car-management

  The car-management module contains the use cases a salesperson executes, like handover a car to a customer and return one. 

You might want to explore the clean architecture further by implementing more use cases.

Here is a list of user story proposals:

1. As a sales manager I would like to define multiple rental rates with
   a different, but never overlapping, validity period, so that rates can vary over time.
2. As a sales manager I would like to specify different stations where the customer can pick up cars.
3. As a customer I would like to get offers for stations in the city where I live (my address).

## Details

### User Interface

The example application comes with a very basic swing ui that you can use to try out the application. Since it
is a basic ui it neither has any convenience features, like validation, nor it is very nice. The purpose of the
user interface is to provide a simple GUI to access the use cases. It also shows the basics of how use cases are
connected to controllers and how controllers use presenters in order to update models. Feel to implement a web server
and provide rest controllers in order to support a web ui.

![Simple Swing UI](src/site/resources/ui-screenshot.png)

### Database

The application uses a H2 file database that is automatically created in the working directory when you start the 
application. This will contain some test data. If you need to reset the database, just delete the carrental.mv.db in the 
working directory.

The example data is the same as the data that the unit tests use. So you can also browse it here:

- [car booking test data](car-booking/entities/src/site/markdown/TestFixtures.md)

