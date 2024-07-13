# Hi, all!

This is my sandbox project with Scala 3 and ZIO stack.

Inspired by [zio-petclinic](https://github.com/zio/zio-petclinic). Thanks for developers!

I'm not a professional developer. This is just an educational project. I will be glad to receive feedback.

## Act I

1. Using latest version of ZIO stack (12.07.2024) -- http, quill
2. build.sbt focus on things only I know. Simpler is better to start for me.
3. More practice in ZIO DI by Service Pattern with ZLayers
4. Create some CRUD operations on Account and Customer entities
5. ❓ Trying to use Scala 3 opaque types for Id's (scala 3 analogue for value classes). Little confusing about implicits for codecs and etc.
I don't know how I could optimize this. Quill does not automatically decode opaque types
6. ✅ Replace opaque types from par.5 with NewType by `"io.github.kitlangton" %% "neotype"`. Full interoperable with zio-json and zio-quill. Like it.
7. Add first simple test. 
    - There were some difficulties with the `provide()`. It matters where you use this `provide()`, for a `test` or a `suite`.
    -  `TestAspect.withLiveRandom` use for live random service impl. Usefull if run test several times and have unique constraint in DB (for example). Otherwise SQL constaraint violation error occurs.

## Future

- more business operations with accounts and customers
- add test
- testcontainers
- programmatic migration
- add logging
- add errors and error handling
- add configuration
- add metrics
-

## Using

1. Start postgres via `docker-compose up`
2. In sbt shell run `flywayMigrate`
3. Run application and trying `curl` and `psql` (routes in **Routes.scala)