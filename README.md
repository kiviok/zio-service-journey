# Hi, all!

This is my sandbox project with Scala 3 and ZIO stack.

Inspired by [zio-petclinic](https://github.com/zio/zio-petclinic). Thanks for developers!

I'm not a professional developer. This is just an educational project. I will be glad to receive feedback.

## Act I

1. Using latest version of ZIO stack (12.07.2024) -- http, quill
2. build.sbt focus on things only I know. Simpler is better to start for me.
2. More practice in ZIO DI by Service Pattern with ZLayers
3. Create some CRUD operations on Account and Customer entities
4. Trying to use Scala 3 opaque types for Id's (scala 3 analogue for value classes). Little confusing about implicits for codecs and etc.
I don't know how I could optimize this. Quill does not automatically decode opaque types.(?)

## Future

- more business operations with accounts and customers
- add test
- add logging
- add errors and error handling
- add configuration
- add metrics
-

## Using

1. Start postgres via `docker-compose up`
2. In sbt shell run `flywayMigrate`
3. Run application and trying `curl` and `psql` (routes in **Routes.scala)