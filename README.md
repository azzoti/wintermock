WinterMock
==========

[![Build Status](https://api.travis-ci.org/azzoti/wintermock.svg?branch=master)](https://travis-ci.org/azzoti/org.lazyluke.wintermock.wintermock)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.lazyluke/org.lazyluke.wintermock.wintermock/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.lazyluke/org.lazyluke.wintermock.wintermock)

A tool to do whatever...


##Features

- Feature 1
- Feature 2 
  - Bullet point 1
  - Bullet point 2
  
##Restrictions
- Impure functions that modify their arguments will not work well. 
- Method parameters or return values containing Maps where the Key is not a simple JSON type will not work without some extra work.  See http://stackoverflow.com/questions/11246748/deserializing-non-string-map-keys-with-jackson for how to make Map<SomeType, SomeOtherType> work
  - See PersonKeySerializer and PersonKeyDeserializer and PersonKeyJacksonModule for how this can be made to work for a java map of type Map<Person, ...>
- Infinite Recursion
  - http://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion

##Usage

To blah:

``
    Blah.testMethod(Param.class);
``


##Best Practice

You should ... 



##Issues

Please ensure you are running the latest release of WinterMock before logging an issue.
If the problem still persists, please log an issue at https://github.com/azzoti/org.lazyluke.wintermock.wintermock/issues including a sample that demonstrates the defect.
