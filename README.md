# Java Super Builders

This project provides an easy way of creating builders for objects.
Those builders should be able to help you create new instances of objects in the most convenient way you can think of.
Primarily they're test/oriented, but can be freely used also in production code.

## Motivation

During writing tests you'll need to create many objects.
Creation process must be quite often very precisely controlled in order for the final object to be in desired state, useful to the test.
Builder pattern fits here very well.
Unfortunately, they tend to grow in size and complexity as their usefulness grows.
It's not rocket science, but there are a few aspects to consider. 
Still, most of the code is just repeating itself, just for different properties.
Writing and maintaining builders becomes tedious.

That's why the idea of Super Builders came to be, I simply didn't want to write them myself.

## Goals

* generating builders for classes, records and maybe interfaces
* generating builders for class hierarchies, with a possibility to set fields from superclasses
* no runtime dependency, only compile-time
* pre-filling builders with random data
* for fields with complex types
  * ability to also build those with their own builders
  * alter already partially configured fields with lambdas
* collections support - clear, addSingle, addAll, set
* thread safety, builders are immutable, every change creates a new builder instance
* possibility to provide own defaults or implementations
* in general - make the builders as powerful as humanly possible so that their users can create new objects with minimal fuss
