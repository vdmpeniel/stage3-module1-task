package com.mjc.school.helper;

import static com.mjc.school.helper.Constant.AUTHOR_ID;
import static com.mjc.school.helper.Constant.AUTHOR_ID_ENTER;
import static com.mjc.school.helper.Constant.NEWS_CONTENT_ENTER;
import static com.mjc.school.helper.Constant.NEWS_ID;
import static com.mjc.school.helper.Constant.NEWS_ID_ENTER;
import static com.mjc.school.helper.Constant.NEWS_TITLE_ENTER;
import static com.mjc.school.helper.Constant.NUMBER_OPERATION_ENTER;
import static com.mjc.school.helper.Operations.CREATE_NEWS;
import static com.mjc.school.helper.Operations.GET_ALL_NEWS;
import static com.mjc.school.helper.Operations.GET_NEWS_BY_ID;
import static com.mjc.school.helper.Operations.REMOVE_NEWS_BY_ID;
import static com.mjc.school.helper.Operations.UPDATE_NEWS;

import java.util.Scanner;

import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.exceptions.ValidatorException;

public class MenuHelper {

  public void printMainMenu() {
    System.out.println(NUMBER_OPERATION_ENTER);
    for (Operations operation : Operations.values()) {
      System.out.println(operation.getOperationWithNumber());
    }
  }

  public void getNews(NewsController newsController) {
    System.out.println(GET_ALL_NEWS.getOperation());
    newsController.readAll().forEach(System.out::println);
  }

  public void getNewsById(NewsController newsController, Scanner keyboard) {
    System.out.println(GET_NEWS_BY_ID.getOperation());
    System.out.println(NEWS_ID_ENTER);
    System.out.println(newsController.readById(getKeyboardNumber(NEWS_ID, keyboard)));
  }

  public void createNews(NewsController newsController, Scanner keyboard) {
    NewsDtoRequest dtoRequest = null;
    boolean isValid = false;
    while (!isValid) {
      try {
        System.out.println(CREATE_NEWS.getOperation());
        System.out.println(NEWS_TITLE_ENTER);
        String title = keyboard.nextLine();
        System.out.println(NEWS_CONTENT_ENTER);
        String content = keyboard.nextLine();
        System.out.println(AUTHOR_ID_ENTER);
        Long authorId = getKeyboardNumber(AUTHOR_ID, keyboard);
        dtoRequest = new NewsDtoRequest(null, title, content, authorId);
        isValid = true;
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }
    System.out.println(newsController.create(dtoRequest));
  }

  public void updateNews(NewsController newsController, Scanner keyboard) {
    NewsDtoRequest dtoRequest = null;
    boolean isValid = false;
    while (!isValid) {
      try {
        System.out.println(UPDATE_NEWS.getOperation());
        System.out.println(NEWS_ID_ENTER);
        Long newsId = getKeyboardNumber(NEWS_ID, keyboard);
        System.out.println(NEWS_TITLE_ENTER);
        String title = keyboard.nextLine();
        System.out.println(NEWS_CONTENT_ENTER);
        String content = keyboard.nextLine();
        System.out.println(AUTHOR_ID_ENTER);
        Long authorId = getKeyboardNumber(AUTHOR_ID, keyboard);
        dtoRequest = new NewsDtoRequest(newsId, title, content, authorId);
        isValid = true;
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    }
    System.out.println(newsController.update(dtoRequest));
  }

  public void deleteNews(NewsController newsController, Scanner keyboard) {
    System.out.println(REMOVE_NEWS_BY_ID.getOperation());
    System.out.println(NEWS_ID_ENTER);
    System.out.println(newsController.deleteById(getKeyboardNumber(NEWS_ID, keyboard)));
  }

  private long getKeyboardNumber(String params, Scanner keyboard) {
    long enter;
    try {
      enter = keyboard.nextLong();
      keyboard.nextLine();
    } catch (Exception ex) {
      keyboard.nextLine();
      throw new ValidatorException(
          String.format(ServiceErrorCode.VALIDATE_INT_VALUE.getMessage(), params));
    }
    return enter;
  }
}
