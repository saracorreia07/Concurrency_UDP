//GOAL

This project was created with the goal of implementing core Java concepts (as multi-threading, concurrency, etc...), in order to solidify them. 

I built a "client-server" architecture, while implementing concurrency between objects, using multithreading.

I created a communication mechanism between two components, that is based in a concurrent queue. 
One component detects and reads messages from the shell, and converts them into a hexadecimal message, before it sends it to a second component, by using a socket. This second component, reconverts theses messages into plain text and show it in the shell, notifying the first component that the messages were shown, or not.

//TECHNOLOGIES

Java,
intelliJ


