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

import java.io.File;
import java.util.Collection;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class PreferencesTask extends DefaultTask {

  private File outputDir;
  private Collection<File> sourceDirs;

  public void setOutputDir(File outputDir) {
    this.outputDir = outputDir;
  }

  public void setSourceDirs(Collection<File> sourceDirs) {
    this.sourceDirs = sourceDirs;
  }

  @TaskAction
  public void generate() {
    if (outputDir == null) {
      System.err.println("No output dictionary for PreferencesTask.");
      return;
    }
    if (sourceDirs == null) {
      System.err.println("No source dictionaries for PreferencesTask.");
    }

    System.out.println(outputDir);
    System.out.println(sourceDirs);
  }
}
