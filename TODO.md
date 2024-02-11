To read:
* https://stackoverflow.com/questions/10483423/java-code-transform-at-compile-time

Or maybe use [Spoon](https://spoon.gforge.inria.fr/) with its [Maven](https://github.com/SpoonLabs/spoon-maven-plugin) or [Gradle](https://github.com/SpoonLabs/spoon-gradle-plugin) plugins.

Alibaba [proposes](https://www.alibabacloud.com/blog/599443) usage of [JavacTrees](https://www.javadoc.io/static/io.earcam.wrapped/jdk.compiler/1.8.132/com/sun/tools/javac/api/JavacTrees.html), but it has been deleted since.

java -cp spoon-core-10.4.2-jar-with-dependencies.jar spoon.Launcher -i src/test/java/tech/bilski/superbuilders/JavaBean.java --gui
