gerrit-notify-patch: Gerrit Notify Patch plugin
==================

* Author: rinrinne a.k.a. rin_ne
* Repository: http://github.com/rinrinne/gerrit-notify-patch
* Release: http://github.com/rinrinne/gerrit-notify-patchset/releases

[![Build Status](https://travis-ci.org/rinrinne/gerrit-notify-patchset.png?branch=master)](https://travis-ci.org/rinrinne/gerrit-notify-patchset)

Synopsis
----------------------

This is a Gerrit plugin.

This can publish `patchset-notified` event for the latest patchset in a change to gerrit event stream.
`Notify` button is added to revision view.

This plugin works on Gerrit 2.8 or later.

Environments
---------------------

* `linux`
  * `java-1.7`
    * `gradle`

Build
---------------------

To build plugin with maven.

    ./gradlew build

Using another version API
--------------------------

Now avaliable for Gerrit 2.9 by default. If you want to use it on another version of Gerrit, please try the below.

    ./gradlew build -PapiVersion=2.8

Reference
--------------------------

* [Event]

[Event]: https://github.com/rinrinne/gerrit-notify-patchset/blob/master/src/main/resources/Documentation/event.md


History
---------------------

* 1.0
  *  First release

License
---------------------

The Apache Software License, Version 2.0

Copyright
---------------------

Copyright (c) 2015 rinrinne a.k.a. rin_ne
