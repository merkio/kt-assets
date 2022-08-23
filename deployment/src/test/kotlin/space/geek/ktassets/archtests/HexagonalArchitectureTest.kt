package space.geek.ktassets.archtests

import com.tngtech.archunit.core.domain.JavaClass.Predicates.equivalentTo
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.library.Architectures
import space.geek.ktassets.Application

@AnalyzeClasses(packages = ["space.geek.ktassets"])
internal class HexagonalArchitectureTest {

    @ArchTest
    val layeredArchitectureTest: Architectures.LayeredArchitecture = Architectures.layeredArchitecture()
        .consideringAllDependencies()
        .layer("Messaging").definedBy("space.geek.ktassets.messaging..")
        .layer("Application").definedBy("space.geek.ktassets.application..")
        .layer("Domain").definedBy("space.geek.ktassets.domain..")
        .layer("Repository").definedBy("space.geek.ktassets.repository..")
        .layer("API").definedBy("space.geek.ktassets.api..")
        .layer("Remote").definedBy("space.geek.ktassets.remote..")
        .layer("Deployment").definedBy(equivalentTo(Application::class.java))
        .whereLayer("Application").mayOnlyBeAccessedByLayers("Messaging", "API")
        .whereLayer("Repository").mayNotBeAccessedByAnyLayer()
        .whereLayer("Messaging").mayNotBeAccessedByAnyLayer()
        .whereLayer("Remote").mayNotBeAccessedByAnyLayer()
        .whereLayer("API").mayNotBeAccessedByAnyLayer()
//        .whereLayer("Domain").mayOnlyBeAccessedByLayers(
//            "API",
//            "Remote",
//            "Messaging",
//            "Application",
//            "Repository"
//        )
}
