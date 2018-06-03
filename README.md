[![codebeat badge](https://codebeat.co/badges/301136ae-f0fa-4e5d-802b-6b916dd3331f)](https://codebeat.co/projects/github-com-wrparrish-tubetime-master)

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [TubeTime](#tubetime)
  - [Project Quality](#project-quality)
  - [Project Organization](#project-organization)
    - [app - Android Application Module](#app---android-application-module)
      - [Redux](#redux)
    - [Data - Android Library Module](#data---android-library-module)
    - [Domain - Kotlin Module](#domain---kotlin-module)
  - [Project Architecture](#project-architecture)
  - [Tests](#tests)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# TubeTime

## Project Quality
TubeTime is evaluated by a static analysis / style tool called CodeBeat. The projects  score is available [here](https://codebeat.co/projects/github-com-wrparrish-tubetime-master)


## Project Organization
TubeTime is delivered as a multi-module project, in an effort to establish a clear separation of concerns from an architectural standpoint.

The Modules are as follows

* app
* Data
* Domain
`
A module just for the presentation layer was  out of scope for this task.

###  app - Android Application Module
This module  is a "delivery mechanism", it contains the necessary concrete implementations of the more abstract policies of the higher level modules.  It is responsible for creating all the various concretions that the more abstract lower layers  use. Since there is no separate module for presentation, the Model in our traditional  MVP setup also serves as a Redux Store.
#### Redux
Redux is a way to manage the synchronization of  application state, and a user interface. It has a small and simple api, and a rich set of middleware that can be plugged in to accomplish various tasks. This implementation of it is  documented [here](https://suas.readme.io/docs).



### Data - Android Library Module
This module provides additional concrete implementations of the higher level "data source"  policies established by the domain layer. It can be compiled and consumed as an android library  / .aar. It also contains mapping classes from the data models to domain models, however these are mostly a  1-1 mapping with default values provided given the scope / time.


### Domain - Kotlin Module
This module establishes basic data structures that match the expected modeling of the problem space ( in this case, showing stations / stops and arrival).


## Project Architecture
This project adheres to the principles of [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html)  where possible (for the sake of time).  Layers are crossed by using RxJava Observables.

## Tests
A few simple tests covering the interactions with the "StationState" managed by redux,  are provided in the Android module and can be ran by using ./gradlew test.
 The android / ui layer was kept relatively dumb,  and simply consumes the state emitted by the redux store.






