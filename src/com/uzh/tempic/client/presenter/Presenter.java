package com.uzh.tempic.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;

import javax.servlet.ServletException;

public interface Presenter {
  void go(final HasWidgets container) throws Throwable;
}
