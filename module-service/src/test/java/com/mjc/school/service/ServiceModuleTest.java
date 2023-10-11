package com.mjc.school.service;

import static com.mjc.school.service.utils.Utils.getServiceImplementationPackageClasses;
import static com.mjc.school.service.utils.Utils.getServicePackage;
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

class ServiceModuleTest {

  @Test
  public void servicePackageShouldBeDefinedInServiceModule() {
    assertTrue(
        getServicePackage().size() > 0,
        "The module implementation should have 'service 'package. Example:'com.mjc.school.service'.");
  }

  @Test
  public void implementationPackageShouldBeDefinedInServiceModule() {
    assertTrue(
        getServiceImplementationPackageClasses().size() > 0,
        "The module implementation should have 'implementation' or 'impl' package. Example:'com.mjc.school.service.implementation'.");
  }

  @Test
  public void serviceClassShouldHavePartOfServiceClassName() {
    List<Class> serviceClasses = getServiceImplementationPackageClasses();
    assertTrue(
        serviceClasses.size() > 0,
        "Service classes should be defined in 'com.mjc.school.service.implementation' or 'com.mjc.school.service.impl' package.");
  }

  @Test
  public void serviceClassShouldHaveConstructor() {
    List<Class> serviceClasses = getServiceImplementationPackageClasses();
    assertTrue(
        serviceClasses.size() > 0,
        "Service classes should be defined in 'com.mjc.school.service.implementation' or 'com.mjc.school.service.impl' package.");
    Constructor[] constructors = serviceClasses.get(0).getConstructors();
    assertTrue(constructors.length > 0, "Service class should have constructor.");
  }

  @Test
  public void serviceClassShouldHaveParametrizedReferenceToRepositoryLayer() {
    List<Class> serviceClasses = getServiceImplementationPackageClasses();
    assertTrue(
        serviceClasses.size() > 0,
        "Service classes should be defined in 'com.mjc.school.service.implementation' or 'com.mjc.school.service.impl' package.");
    Field[] fields = serviceClasses.get(0).getDeclaredFields();
    List<Field> fieldList =
        Arrays.stream(fields)
            .filter(field -> field.getName().contains("Repository"))
            .collect(Collectors.toList());
    assertEquals(
        1, fieldList.size(), "Service class should have 'Repository' part of the name reference.");
    assertEquals(
        "private final",
        Modifier.toString(fieldList.get(0).getModifiers()),
        "Repository reference should contains 'private final' modifications.");
    assertTrue(
        fieldList.get(0).getGenericType().getTypeName().contains("Model"),
        "Repository reference should have generalized type with 'Model' part of the type name.");
  }

  @Test
  public void serviceClassShouldHaveReferenceToValidator() {
    List<Class> serviceClasses = getServiceImplementationPackageClasses();
    assertTrue(
        serviceClasses.size() > 0,
        "Service classes should be defined in 'com.mjc.school.service.implementation' or 'com.mjc.school.service.impl' package.");
    Field[] fields = serviceClasses.get(0).getDeclaredFields();
    List<Field> listFields =
        Arrays.stream(fields)
            .filter(field -> field.getName().contains("Validator"))
            .collect(Collectors.toList());
    assertEquals(
        1, listFields.size(), "Service class should have 'Validator' part of the name reference.");
    assertEquals(
        "private final",
        Modifier.toString(listFields.get(0).getModifiers()),
        "Validator reference should contains 'private final' modifications.");
  }

  @Test
  public void serviceLayerShouldNotHaveFieldWithPartNameOfControllerLayer() {
    List<Class> serviceClasses = getServiceImplementationPackageClasses();
    assertTrue(
        serviceClasses.size() > 0,
        "Service classes should be defined in 'com.mjc.school.service.implementation' or 'com.mjc.school.service.impl' package.");
    Field[] fields = serviceClasses.get(0).getDeclaredFields();
    List<Field> fieldList =
        Arrays.stream(fields)
            .filter(field -> field.getName().toLowerCase().contains("controller"))
            .collect(Collectors.toList());
    assertEquals(
        0,
        fieldList.size(),
        "Service layer should not have field with part name of 'Controller' layer.");
  }

  @Test
  public void serviceClassShouldHavePartOfReadAllMethodName() {
    List<Class> serviceClasses = getServiceImplementationPackageClasses();
    assertTrue(
        serviceClasses.size() > 0,
        "Service classes should be defined in 'com.mjc.school.service.implementation' or 'com.mjc.school.service.impl' package.");
    String methodNames = getWrittenMethodsNames(serviceClasses).toString();
    assertTrue(
        methodNames.contains("readAll"),
        "Service class should have 'readAll' part of the name method.");
  }

  @Test
  public void serviceClassShouldHavePartOfReadByMethodName() {
    List<Class> serviceClasses = getServiceImplementationPackageClasses();
    assertTrue(
        serviceClasses.size() > 0,
        "Service classes should be defined in 'com.mjc.school.service.implementation' or 'com.mjc.school.service.impl' package.");
    String methodNames = getWrittenMethodsNames(serviceClasses).toString();
    assertTrue(
        methodNames.contains("readBy"),
        "Service class should have 'readBy' part of the name method.");
  }

  @Test
  public void serviceClassShouldHavePartOfCreateMethodName() {
    List<Class> serviceClasses = getServiceImplementationPackageClasses();
    assertTrue(
        serviceClasses.size() > 0,
        "Service classes should be defined in 'com.mjc.school.service.implementation' or 'com.mjc.school.service.impl' package.");
    String methodNames = getWrittenMethodsNames(serviceClasses).toString();
    assertTrue(
        methodNames.contains("create"),
        "Service class should have 'create' part of the name method.");
  }

  @Test
  public void serviceClassShouldHavePartOfUpdateMethodName() {
    List<Class> serviceClasses = getServiceImplementationPackageClasses();
    assertTrue(
        serviceClasses.size() > 0,
        "Service classes should be defined in 'com.mjc.school.service.implementation' or 'com.mjc.school.service.impl' package.");
    String methodNames = getWrittenMethodsNames(serviceClasses).toString();
    assertTrue(
        methodNames.contains("update"),
        "Service class should have 'update' part of the name method.");
  }

  @Test
  public void serviceClassShouldHavePartOfDeleteMethodName() {
    List<Class> serviceClasses = getServiceImplementationPackageClasses();
    assertTrue(
        serviceClasses.size() > 0,
        "Service classes should be defined in 'com.mjc.school.service.implementation' or 'com.mjc.school.service.impl' package.");
    String methodNames = getWrittenMethodsNames(serviceClasses).toString();
    assertTrue(
        methodNames.contains("delete"),
        "Service class should have 'delete' part of the name method.");
  }

  @Test
  public void readAllServiceMethodShouldHaveOnlyReturnType() {
    List<Class> serviceClasses = getServiceImplementationPackageClasses();
    assertTrue(
        serviceClasses.size() > 0,
        "Service classes should be defined in 'com.mjc.school.service.implementation' or 'com.mjc.school.service.impl' package.");
    List<Method> methods = getWrittenMethods(serviceClasses);
    for (Method method : methods) {
      if (method.getName().contains("readAll")) {
        assertEquals(List.class, method.getReturnType(), "ReadAll method should return List type.");
        assertEquals(0, method.getParameters().length, "ReadAll method should not have parameters.");
      }
    }
  }

  @Test
  public void readByServiceMethodShouldHaveReturnTypeAndTakeParameter() {
    List<Class> serviceClasses = getServiceImplementationPackageClasses();
    assertTrue(
        serviceClasses.size() > 0,
        "Service classes should be defined in 'com.mjc.school.service.implementation' or 'com.mjc.school.service.impl' package.");
    List<Method> methods = getWrittenMethods(serviceClasses);
    for (Method method : methods) {
      if (method.getName().contains("readBy")) {
        assertTrue(
            method.getReturnType().getName().toLowerCase().contains("dto"),
            "ReadBy method should return Object 'Dto' part of the type name.");
        assertEquals(1, method.getParameters().length, "ReadBy method should take 1 parameter.");
        Class<?>[] parameter = method.getParameterTypes();
        assertTrue(
            parameter[0].getTypeName().toLowerCase().contains("java.lang.long"),
            "ReadBy method parameter should be Long type.");
      }
    }
  }

  @Test
  public void createServiceMethodShouldHaveReturnTypeAndTakeParameter() {
    List<Class> serviceClasses = getServiceImplementationPackageClasses();
    assertTrue(
        serviceClasses.size() > 0,
        "Service classes should be defined in 'com.mjc.school.service.implementation' or 'com.mjc.school.service.impl' package.");
    List<Method> methods = getWrittenMethods(serviceClasses);
    for (Method method : methods) {
      if (method.getName().contains("create")) {
        assertTrue(
            method.getReturnType().getName().toLowerCase().contains("dto"),
            "Create method should return Object 'Dto' part of the type name.");
        assertEquals(1, method.getParameters().length, "Create method should take 1 parameter.");
        Class<?>[] parameter = method.getParameterTypes();
        assertTrue(
            parameter[0].getTypeName().toLowerCase().contains("dto"),
            "Create method parameter should contains Object 'Dto' part of the type name.");
      }
    }
  }

  @Test
  public void updateServiceMethodShouldHaveReturnTypeAndTakeParameter() {
    List<Class> serviceClasses = getServiceImplementationPackageClasses();
    assertTrue(
        serviceClasses.size() > 0,
        "Service classes should be defined in 'com.mjc.school.service.implementation' or 'com.mjc.school.service.impl' package.");
    List<Method> methods = getWrittenMethods(serviceClasses);
    for (Method method : methods) {
      if (method.getName().contains("update")) {
        assertTrue(
            method.getReturnType().getName().toLowerCase().contains("dto"),
            "Update method method should return Object 'Dto' part of the type name.");
        assertEquals(1, method.getParameters().length, "Update method should take 1 parameter.");
        Class<?>[] parameter = method.getParameterTypes();
        assertTrue(
            parameter[0].getTypeName().toLowerCase().contains("dto"),
            "Update method parameter should contains Object 'Dto' part of the type name.");
      }
    }
  }

  @Test
  public void deleteServiceMethodShouldHaveReturnTypeAndTakeParameter() {
    List<Class> serviceClasses = getServiceImplementationPackageClasses();
    assertTrue(
        serviceClasses.size() > 0,
        "Service classes should be defined in 'com.mjc.school.service.implementation' or 'com.mjc.school.service.impl' package.");
    List<Method> methods = getWrittenMethods(serviceClasses);
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
