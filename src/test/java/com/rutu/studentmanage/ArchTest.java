package com.rutu.studentmanage;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.rutu.studentmanage");

        noClasses()
            .that()
            .resideInAnyPackage("com.rutu.studentmanage.service..")
            .or()
            .resideInAnyPackage("com.rutu.studentmanage.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.rutu.studentmanage.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
