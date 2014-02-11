# Albus
Albus is an open-source crash reporter and analytics platform designed to give developers complete knowledge of its inner workings and ability to customize it to their needs.

Albus means white [in Latin](http://en.wiktionary.org/wiki/albus) and expresses the true desire of the library. Crash reporting should never be a black box - developers should be aware of details like what data they are passing over the network, how often they are doing so, how they can delete their data and how they can mixin their own flavor to the crash reporting. Crash reporting shouldn't be a black box, it should be an Albus box.

You can find the server repo [here](https://github.com/ronshapiro/albus).

## Usage

TODO edit this section with a tutorial.

## Goals
1. Create a simple, lightweight library that requires little out-of-the-box configuration, has a miniscule performance overhead, and provides great data for developers.
2. Allow users to own their end-to-end Albus experience - create custom plugins, deploy their own servers, and analyze their own data
3. Albus should not conflict with other libraries: Crashlytics, Crittercism, New Relic, and more should all work side-by-side with Albus.

## License
```
Copyright 2014 Ron Shapiro

Licensed under the Apache License, Version 2.0 (the "License"); you may not use
this file except in compliance with the License.

You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.
```