package com.mjc.school.repository;

import static com.mjc.school.repository.utils.Utils.getRepositoryImplementationPackageClasses;
import static com.mjc.school.repository.utils.Utils.getRepositoryPackage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class RepositoryModuleTest {

  @Test
  public void repositoryPackageShouldBeDefinedInRepositoryModule() {
    assertTrue(
        getRepositoryPackage().size() > 0,
        "The module implementation should have 'repository 'package. Example:'com.mjc.school.repository'.");
  }

  @Test
  public void implementationPackageShouldBeDefinedInRepositoryModule() {
    assertTrue(
        getRepositoryImplementationPackageClasses().size() > 0,
        "The module implementation should have 'implementation' or 'impl' package. Example:'com.mjc.school.repository.implementation'.");
  }

  @Test
  public void repositoryClassShouldHavePartOfRepositoryClassName() {
    List<Class> repositoryClasses = getRepositoryImplementationPackageClasses();
    assertTrue(
        repositoryClasses.size() > 0,
        "Repository classes should be defined in 'com.mjc.school.repository.implementation' or 'com.mjc.school.repository.impl' package.");
  }

  @Test
  public void repositoryClassShouldHaveConstructor() {
    List<Class> repositoryClasses = getRepositoryImplementationPackageClasses();
    assertTrue(
        repositoryClasses.size() > 0,
        "Repository classes should be defined in 'com.mjc.school.repository.implementation' or 'com.mjc.school.repository.impl' package.");
    Constructor[] constructors = repositoryClasses.get(0).getConstructors();
    assertTrue(constructors.length > 0, "Repository class should have constructor.");
  }

  @Test
  public void repositoryClassShouldHaveReferenceToDataSource() {
    List<Class> repositoryClasses = getRepositoryImplementationPackageClasses();
    assertTrue(
        repositoryClasses.size() > 0,
        "Repository classes should be defined in 'com.mjc.school.repository.implementation' or 'com.mjc.school.repository.impl' package.");
    Field[] fields = repositoryClasses.get(0).getDeclaredFields();
    List<Field> dataSource =
        Arrays.stream(fields)
            .filter(field -> field.getName().toLowerCase().contains("datasource"))
            .collect(Collectors.toList());
    assertEquals(
        1,
        dataSource.size(),
        "Repository class should contains 'DataSource' part of the name reference.");
    assertEquals(
        "private final",
        Modifier.toString(dataSource.get(0).getModifiers()),
        "DataSource reference should contains 'private final' modifications.");
  }

  @Test
  public void repositoryLayerShouldNotHaveFieldWithPartNameOfServiceLayer() {
    List<Class> repositoryClasses = getRepositoryImplementationPackageClasses();
    assertTrue(
        repositoryClasses.size() > 0,
        "Repository classes should be defined in 'com.mjc.school.repository.implementation' or 'com.mjc.school.repository.impl' package.");
    Field[] fields = repositoryClasses.get(0).getDeclaredFields();
    List<Field> layers =
        Arrays.stream(fields)
            .filter(field -> field.getName().toLowerCase().contains("service"))
            .collect(Collectors.toList());
    assertEquals(
        layers.size(),
        0,
        "Repository layer should not have field with part name of 'Service' layer.");
  }

  @Test
  public void repositoryLayerShouldNotHaveFieldWithPartNameOfControllerLayer() {
    List<Class> repositoryClasses = getRepositoryImplementationPackageClasses();
    assertTrue(
        repositoryClasses.size() > 0,
        "Repository classes should be defined in 'com.mjc.school.repository.implementation' or 'com.mjc.school.repository.impl' package.");
    Field[] fields = repositoryClasses.get(0).getDeclaredFields();
    List<Field> layers =
        Arrays.stream(fields)
            .filter(field -> field.getName().toLowerCase().contains("controller"))
            .collect(Collectors.toList());
    assertEquals(
        0,
        layers.size(),
        "Repository layer should not have field with part name of 'Controller' layer.");
  }

  @Test
  public void repositoryClassShouldHavePartOfReadAllMethodName() {
    List<Class> repositoryClasses = getRepositoryImplementationPackageClasses();
    assertTrue(
        repositoryClasses.size() > 0,
        "Repository classes should be defined in 'com.mjc.school.repository.implementation' or 'com.mjc.school.repository.impl' package.");
    String methodNames = getWrittenMethodsNames(repositoryClasses).toString();
    assertTrue(
        methodNames.contains("readAll"),
        "Repository class should have 'readAll' part of the name method.");
  }

  @Test
  public void repositoryClassShouldHavePartOfReadByMethodName() {
    List<Class> repositoryClasses = getRepositoryImplementationPackageClasses();
    assertTrue(
        repositoryClasses.size() > 0,
        "Repository classes should be defined in 'com.mjc.school.repository.implementation' or 'com.mjc.school.repository.impl' package.");
    String methodNames = getWrittenMethodsNames(repositoryClasses).toString();
    assertTrue(
        methodNames.contains("readBy"),
        "Repository class should have 'readBy' part of the name method.");
  }

  @Test
  public void repositoryClassShouldHavePartOfCreateMethodName() {
    List<Class> repositoryClasses = getRepositoryImplementationPackageClasses();
    assertTrue(
        repositoryClasses.size() > 0,
        "Repository classes should be defined in 'com.mjc.school.repository.implementation' or 'com.mjc.school.repository.impl' package.");
    String methodNames = getWrittenMethodsNames(repositoryClasses).toString();
    assertTrue(
        methodNames.contains("create"),
        "Repository class should have 'create' part of the name method.");
  }

  @Test
  public void repositoryClassShouldHavePartOfUpdateMethodName() {
    List<Class> repositoryClasses = getRepositoryImplementationPackageClasses();
    assertTrue(
        repositoryClasses.size() > 0,
        "Repository classes should be defined in 'com.mjc.school.repository.implementation' or 'com.mjc.school.repository.impl' package.");
    String methodNames = getWrittenMethodsNames(repositoryClasses).toString();
    assertTrue(
        methodNames.contains("update"),
        "Repository class should have 'update' part of the name method.");
  }

  @Test
  public void repositoryClassShouldHavePartOfDeleteMethodName() {
    List<Class> repositoryClasses = getRepositoryImplementationPackageClasses();
    assertTrue(
        repositoryClasses.size() > 0,
        "Repository classes should be defined in 'com.mjc.school.repository.implementation' or 'com.mjc.school.repository.impl' package.");
    String methodNames = getWrittenMethodsNames(repositoryClasses).toString();
    assertTrue(
        methodNames.contains("delete"),
        "Repository class should have 'delete' part of the name method.");
  }

  @Test
  public void readAllRepositoryMethodShouldHaveOnlyReturnType() {
    List<Class> repositoryClasses = getRepositoryImplementationPackageClasses();
    assertTrue(
        repositoryClasses.size() > 0,
        "Repository classes should be defined in 'com.mjc.school.repository.implementation' or 'com.mjc.school.repository.impl' package.");
    List<Method> methods = getWrittenMethods(repositoryClasses);
    for (Method method : methods) {
      if (method.getName().contains("readAll")) {
        assertEquals(List.class, method.getReturnType(), "ReadAll method should return List type.");
        assertEquals( 0, method.getParameters().length, "ReadAll method should not have parameters.");
      }
    }
  }

  @Test
  public void readByRepositoryMethodShouldHaveReturnTypeAndTakeParameter() {
    List<Class> repositoryClasses = getRepositoryImplementationPackageClasses();
    assertTrue(
        repositoryClasses.size() > 0,
        "Repository classes should be defined in 'com.mjc.school.repository.implementation' or 'com.mjc.school.repository.impl' package.");
    List<Method> methods = getWrittenMethods(repositoryClasses);
    for (Method method : methods) {
      if (method.getName().contains("readBy")) {
        assertTrue(
            method.getReturnType().getName().contains("Model"),
            "ReadBy method should return Object 'Model' part of the type name.");
        assertEquals(1, method.getParameters().length, "ReadBy method should take 1 parameter.");
        Class<?>[] parameter = method.getParameterTypes();
        assertTrue(
            parameter[0].getTypeName().toLowerCase().contains("java.lang.long"),
            "ReadBy method parameter should be Long type.");
      }
    }
  }

  @Test
  public void createRepositoryMethodShouldHaveReturnTypeAndTakeParameter() {
    List<Class> repositoryClasses = getRepositoryImplementationPackageClasses();
    assertTrue(
        repositoryClasses.size() > 0,
        "Repository classes should be defined in 'com.mjc.school.repository.implementation' or 'com.mjc.school.repository.impl' package.");
    List<Method> methods = getWrittenMethods(repositoryClasses);
    for (Method method : methods) {
      if (method.getName().contains("create")) {
        assertTrue(
            method.getReturnType().getName().contains("Model"),
            "Create method should return Object 'Model' part of the type name.");
        assertEquals( 1, method.getParameters().length,"Create method should take 1 parameter.");
        Class<?>[] parameter = method.getParameterTypes();
        assertTrue(
            parameter[0].getTypeName().contains("Model"),
            "Create method parameter should contains Object 'Model' part of the type name.");
      }
    }
  }

  @Test
  public void updateRepositoryMethodShouldHaveReturnTypeAndTakeParameter() {
    List<Class> repositoryClasses = getRepositoryImplementationPackageClasses();
    assertTrue(
        repositoryClasses.size() > 0,
        "Repository classes should be defined in 'com.mjc.school.repository.implementation' or 'com.mjc.school.repository.impl' package.");
    List<Method> methods = getWrittenMethods(repositoryClasses);
    for (Method method : methods) {
      if (method.getName().contains("update")) {
        assertTrue(
            method.getReturnType().getName().contains("Model"),
            "Update method method should return Object 'Model' part of the type name.");
        assertEquals( 1, method.getParameters().length, "Update method should take 1 parameter.");
        Class<?>[] parameter = method.getParameterTypes();
        assertTrue(
            parameter[0].getTypeName().contains("Model"),
            "Update method parameter should contains Object 'Model' part of the type name.");
      }
    }
  }

  @Test
  public void deleteRepositoryMethodShouldHaveReturnTypeAndTakeParameter() {
    List<Class> repositoryClasses = getRepositoryImplementationPackageClasses();
    assertTrue(
        repositoryClasses.size() > 0,
        "Repository classes should be defined in 'com.mjc.school.repository.implementation' or 'com.mjc.school.repository.impl' package.");
    List<Method> methods = getWrittenMethods(repositoryClasses);
    for (Method method : methods) {
      if (method.getName().contains("delete")) {
        assertEquals(
            Boolean.class,
            method.getReturnType(),
            "Delete method method should return Boolean type.");
        assertEquals(method.getParameters().length, 1, "Delete method should take 1 parameter.");
        Class<?>[] parameter = method.getParameterTypes();
        assertTrue(
            parameter[0].getTypeName().toLowerCase().contains("java.lang.long"),
            "Delete method parameter should be Long type.");
      }
    }
  }

  private List<Method> getWrittenMethods(List<Class> classes) {
    return classes.stream()
            .flatMap(cl -> Arrays.stream(cl.getDeclaredMethods()))
            .filter(m -> !m.isSynthetic())
            .collect(Collectors.toList());
  }

  private Set<String> getWrittenMethodsNames(List<Class> classes) {
    return getWrittenMethods(classes).stream()
            .map(Method::getName)
            .collect(Collectors.toSet());
  }
}
