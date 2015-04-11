# waiter
Automatically exported from code.google.com/p/waiter

# waiter: Effective DNS-SD for Java

For a long time, Java has lacked both a clean, modern DNS library and an effective DNS-SD (Service Discovery) library.
# Why DNS-SD?

DNS-SD is essential in enabling ZeroConf (zero configuration) of deployed Java and non-Java services.
# What's wrong with what we've got?
## Native Implementations are Limited

Whilst there are wrappers for native implementations (Bonjour and Avahi) both of these are very limited in scope; one is only really usable on Macs and Windows (how many developers have the time these days to make custom binaries for Unix systems), and the other lacks sufficient Java interoperability.
## Pure Java

The only pure Java implementation, Strangeberry jmDNS, never seemed to work reliably for me... I've also found it hard to wrap other DNS clients to give me the multicast features I needed. As such, we've written a very clean DNS client library from scratch.
## Test Driven

Large parts of waiter were written using TDD. waiter has a very high test coverage, so giving you the confidence that things are likely to work. Very little of anything else out there is...
# Waiter's other usages
## Waiter as a Java DNS library replacement

The core of waiter is a clean, modern Java DNS library which can be used explicitly instead of the very weak services in the JDK. It be can be used with or without caching, and provides access to far more of the DNS messages, allowing Java programmers to truly leverage DNS in service clusters and distributed systems.
## Waiter: Good example of Java NIO sockets

waiter uses Java NIO sockets, and makes a good example of how to string together enough code to use these effectively and in an exception-friendly manner.
# Extending waiter: Code Evolution, not Reuse

waiter is meant to be extended by you modifying or augmenting the source. It's not a component to be dropped in. As such, there are almost no getters and setters - they violate good OO for a start - and nearly everything happens with constructor injection. There's no XML configuration, no Spring, no dynamic mocks (statically created ones are easier to refactor) and very few third party dependencies. Why? Because it is a library, with one purpose. Those sorts of things belong in systems, not libraries, if they are needed at all... Where choices need to be made, we use strategy patterns or methods in APIs, not complex config in another peculiar language.
