/*
 * Copyright 2017 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hippo.preferences;

/*
 * Created by Hippo on 2/14/2017.
 */

import android.content.SharedPreferences;

/**
 * Fake Preferences.
 */
public abstract class Preferences {

  public Preferences(SharedPreferences shardPref) {
    throw new RuntimeException();
  }

  protected boolean getBoolean(String key, boolean defValue) {
    throw new RuntimeException();
  }

  protected void putBoolean(String key, boolean value) {
    throw new RuntimeException();
  }

  protected int getInt(String key, int defValue) {
    throw new RuntimeException();
  }

  protected void putInt(String key, int value) {
    throw new RuntimeException();
  }

  protected long getLong(String key, long defValue) {
    throw new RuntimeException();
  }

  protected void putLong(String key, long value) {
    throw new RuntimeException();
  }

  protected float getFloat(String key, float defValue) {
    throw new RuntimeException();
  }

  protected void putFloat(String key, float value) {
    throw new RuntimeException();
  }

  protected String getString(String key, String defValue) {
    throw new RuntimeException();
  }

  protected void putString(String key, String value) {
    throw new RuntimeException();
  }

  protected int getDecimalInt(String key, int defValue) {
    throw new RuntimeException();
  }

  protected void putDecimalInt(String key, int value) {
    throw new RuntimeException();
  }
}
