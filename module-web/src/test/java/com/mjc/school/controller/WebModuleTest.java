package com.mjc.school.controller;

import static com.mjc.school.controller.utils.Utils.getControllerImplementationPackageClasses;
import static com.mjc.school.controller.utils.Utils.getControllerPackage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class WebModuleTest {

  @Test
  public void controllerPackageShouldBeDefinedInControllerModule() {
    assertTrue(
        getControllerPackage().size() > 0,
        "The module implementation should have 'controller 'package. Example:'com.mjc.school.controller'.");
  }

  @Test
  public void implementationPackageShouldBeDefinedInControllerModule() {
    assertTrue(
        getControllerImplementationPackageClasses().size() > 0,
        "The module implementation should have 'implementation' or 'impl' package. Example:'com.mjc.school.controller.implementation'.");
  }

  @Test
  public void controllerClassShouldHavePartOfControllerClassName() {
    List<Class> controllerClasses = getControllerImplementationPackageClasses();
    assertTrue(
        controllerClasses.size() > 0,
        "Controller classes should be defined in 'com.mjc.school.controller.implementation' or 'com.mjc.school.controller.impl' package.");
  }

  @Test
  public void controllerClassShouldHaveConstructor() {
    List<Class> controllerClasses = getControllerImplementationPackageClasses();
    assertTrue(
        controllerClasses.size() > 0,
        "Controller classes should be defined in 'com.mjc.school.controller.implementation' or 'com.mjc.school.controller.impl' package.");
    Constructor[] constructors = controllerClasses.get(0).getConstructors();
    assertTrue(constructors.length > 0, "Controller class should have constructor.");
  }

  @Test
  public void controllerClassShouldHaveParametrizedReferenceToServiceLayer() {
    List<Class> controllerClasses = getControllerImplementationPackageClasses();
    assertTrue(
        controllerClasses.size() > 0,
        "Controller classes should be defined in 'com.mjc.school.controller.implementation' or 'com.mjc.school.controller.impl' package.");
    Field[] fields = controllerClasses.get(0).getDeclaredFields();
    List<Field> fieldList =
        Arrays.stream(fields)
            .filter(field -> field.getName().contains("Service"))
            .collect(Collectors.toList());
    assertEquals(
        1, fieldList.size(), "Controller class should have 'Service' part of the name reference.");
    assertEquals(
        "private final",
        Modifier.toString(fieldList.get(0).getModifiers()),
        "Service reference should contains 'private final' modifications.");
    assertTrue(
        fieldList.get(0).getGenericType().getTypeName().contains("Dto"),
        "Service reference should have generalized type with 'Dto' part of the type name.");
  }

  @Test
  public void controllerLayerShouldNotHaveReferenceToRepositoryLayer() {
    List<Class> controllerClasses = getControllerImplementationPackageClasses();
    assertTrue(
        controllerClasses.size() > 0,
        "Controller classes should be defined in 'com.mjc.school.controller.implementation' or 'com.mjc.school.controller.impl' package.");
    Field[] fields = controllerClasses.get(0).getDeclaredFields();
    List<Field> fieldList =
        Arrays.stream(fields)
            .filter(field -> field.getName().toLowerCase().contains("repository"))
            .collect(Collectors.toList());
    assertEquals(
        0, fieldList.size(), "Controller layer should not have reference to 'Repository' layer.");
  }

  @Test
  public void controllerClassShouldHavePartOfReadAllMethodName() {
    List<Class> controllerClasses = getControllerImplementationPackageClasses();
    assertTrue(
        controllerClasses.size() > 0,
        "Controller classes should be defined in 'com.mjc.school.controller.implementation' or 'com.mjc.school.controller.impl' package.");
    Method[] methods = controllerClasses.get(0).getMethods();
    String methodNames =
        Arrays.stream(methods).map(Method::getName).collect(Collectors.toSet()).toString();
    assertTrue(
        methodNames.contains("readAll"),
        "Controller class should have 'readAll' part of the name method.");
  }

  @Test
  public void controllerClassShouldHavePartOfReadByMethodName() {
    List<Class> controllerClasses = getControllerImplementationPackageClasses();
    assertTrue(
        controllerClasses.size() > 0,
        "Controller classes should be defined in 'com.mjc.school.controller.implementation' or 'com.mjc.school.controller.impl' package.");
    Method[] methods = controllerClasses.get(0).getMethods();
    String methodNames =
        Arrays.stream(methods).map(Method::getName).collect(Collectors.toSet()).toString();
    assertTrue(
        methodNames.contains("readBy"),
        "Controller class should have 'readBy' part of the name method.");
  }

  @Test
  public void controllerClassShouldHavePartOfCreateMethodName() {
    List<Class> controllerClasses = getControllerImplementationPackageClasses();
    assertTrue(
        controllerClasses.size() > 0,
        "Controller classes should be defined in 'com.mjc.school.controller.implementation' or 'com.mjc.school.controller.impl' package.");
    Method[] methods = controllerClasses.get(0).getMethods();
    String methodNames =
        Arrays.stream(methods).map(Method::getName).collect(Collectors.toSet()).toString();
    assertTrue(
        methodNames.contains("create"),
        "Controller class should have 'create' part of the name method.");
  }

  @Test
  public void controllerClassShouldHavePartOfUpdateMethodName() {
    List<Class> controllerClasses = getControllerImplementationPackageClasses();
    assertTrue(
        controllerClasses.size() > 0,
        "Controller classes should be defined in 'com.mjc.school.controller.implementation' or 'com.mjc.school.controller.impl' package.");
    Method[] methods = controllerClasses.get(0).getMethods();
    String methodNames =
        Arrays.stream(methods).map(Method::getName).collect(Collectors.toSet()).toString();
    assertTrue(
        methodNames.contains("update"),
        "Controller class should have 'update' part of the name method.");
  }

  @Test
  public void controllerClassShouldHePartOfDeleteMethodName() {
    List<Class> controllerClasses = getControllerImplementationPackageClasses();
    assertTrue(
        controllerClasses.size() > 0,
        "Controller classes should be defined in 'com.mjc.school.controller.implementation' or 'com.mjc.school.controller.impl' package.");
    Method[] methods = controllerClasses.get(0).getMethods();
    String methodNames =
        Arrays.stream(methods).map(Method::getName).collect(Collectors.toSet()).toString();
    assertTrue(
        methodNames.contains("delete"),
        "Controller class should have 'delete' part of the name method.");
  }

  @Test
  public void readAllControllerMethodShouldHaveOnlyReturnType() {
    List<Class> controllerClasses = getControllerImplementationPackageClasses();
    assertTrue(
        controllerClasses.size() > 0,
        "Controller classes should be defined in 'com.mjc.school.controller.implementation' or 'com.mjc.school.controller.impl' package.");
    Method[] methods = controllerClasses.get(0).getMethods();
    for (Method method : methods) {
      if (method.getName().contains("readAll")) {
        assertEquals(List.class, method.getReturnType(),"ReadAll method should return List type.");
        assertEquals(
            0, method.getParameters().length, "ReadAll method should not have parameters.");
      }
    }
  }

  @Test
  public void readByControllerMethodShouldHaveReturnTypeAndTakeParameter() {
    List<Class> controllerClasses = getControllerImplementationPackageClasses();
    assertTrue(
        controllerClasses.size() > 0,
        "Controller classes should be defined in 'com.mjc.school.controller.implementation' or 'com.mjc.school.controller.impl' package.");
    Method[] methods = controllerClasses.get(0).getMethods();
    for (Method method : methods) {
      if (method.getName().contains("readBy")) {
        assertTrue(
            method.getReturnType().getName().contains("Dto"),
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
  public void createControllerMethodShouldHaveReturnTypeAndTakeParameter() {
    List<Class> controllerClasses = getControllerImplementationPackageClasses();
    assertTrue(
        controllerClasses.size() > 0,
        "Controller classes should be defined in 'com.mjc.school.controller.implementation' or 'com.mjc.school.controller.impl' package.");
    Method[] methods = controllerClasses.get(0).getMethods();
    for (Method method : methods) {
      if (method.getName().contains("create")) {
        assertTrue(
            method.getReturnType().getName().contains("Dto"),
            "Create method should return Object 'Dto' part of the type name.");
        assertEquals(1, method.getParameters().length, "Create method should take 1 parameter.");
        Class<?>[] parameter = method.getParameterTypes();
        assertTrue(
            parameter[0].getTypeName().contains("Dto"),
            "Create method parameter should contains Object 'Dto' part of the type name.");
      }
    }
  }

  @Test
  public void updateControllerMethodShouldHaveReturnTypeAndTakeParameter() {
    List<Class> controllerClasses = getControllerImplementationPackageClasses();
    assertTrue(
        controllerClasses.size() > 0,
        "Controller classes should be defined in 'com.mjc.school.controller.implementation' or 'com.mjc.school.controller.impl' package.");
    Method[] methods = controllerClasses.get(0).getMethods();
    for (Method method : methods) {
      if (method.getName().contains("update")) {
        assertTrue(
            method.getReturnType().getName().contains("Dto"),
            "Update method method should return Object 'Dto' part of the type name.");
        assertEquals(1, method.getParameters().length, "Update method should take 1 parameter.");
        Class<?>[] parameter = method.getParameterTypes();
        assertTrue(
            parameter[0].getTypeName().contains("Dto"),
            "Update method parameter should contains Object 'Dto' part of the type name.");
      }
    }
  }

  @Test
  public void deleteControllerMethodShouldHaveReturnTypeAndTakeParameter() {
    List<Class> controllerClasses = getControllerImplementationPackageClasses();
    assertTrue(
        controllerClasses.size() > 0,
        "Controller classes should be defined in 'com.mjc.school.controller.implementation' or 'com.mjc.school.controller.impl' package.");
    Method[] methods = controllerClasses.get(0).getMethods();
    for (Method method : methods) {
      if (method.getName().contains("delete")) {
        assertEquals(
            Boolean.class,
            method.getReturnType(),
            "Delete method method should return Boolean type.");
        assertEquals(1, method.getParameters().length, "Delete method should take 1 parameter.");
        Class<?>[] parameter = method.getParameterTypes();
        assertTrue(
            parameter[0].getTypeName().toLowerCase().contains("java.lang.long"),
            "Delete method parameter should be Long type.");
      }
    }
  }
}
