// Generated by Dagger (https://dagger.dev).
package com.example.gbsbadrsf.Paint.machineloadingpaint;

import dagger.internal.Factory;

@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class SavepaintViewModel_Factory implements Factory<SavepaintViewModel> {
  @Override
  public SavepaintViewModel get() {
    return newInstance();
  }

  public static SavepaintViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SavepaintViewModel newInstance() {
    return new SavepaintViewModel();
  }

  private static final class InstanceHolder {
    private static final SavepaintViewModel_Factory INSTANCE = new SavepaintViewModel_Factory();
  }
}