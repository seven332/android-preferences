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
import java.io.IOException;
import java.util.Collection;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class AndroidPreferencesTask extends DefaultTask {

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
    AndroidPreferencesExtension extension =
        getProject().getExtensions().findByType(AndroidPreferencesExtension.class);
    if (extension == null) {
      System.out.println("No preferences");
      return;
    }

    if (outputDir == null) {
      System.err.println("No output dictionary for PreferencesTask.");
      return;
    }
    if (sourceDirs == null) {
      System.err.println("No source dictionaries for PreferencesTask.");
    }

    for (Preference p: extension.getPreferences()) {
      generatePreference(p);
    }
  }

  private String filePathPart(String raw) {
    return raw.replace('.', File.separatorChar) + ".java";
  }

  private File findInputFile(String p) {
    File f;
    for (File d: sourceDirs) {
      f = new File(d, p);
      if (f.isFile()) {
        return f;
      }
    }

    return null;
  }

  private void generatePreference(Preference p) {
    String fromPart = filePathPart(p.getFrom());
    String toPart = filePathPart(p.getTo());
    File from = findInputFile(fromPart);
    File to = new File(outputDir, toPart);

    if (from == null) {
      System.err.println("Can't find java class source file:" + p.getFrom());
      return;
    }

    try {
      AndroidPreferencesCompiler.compile(from, to, p.getTo());
    } catch (IOException e) {
      System.err.println("Can't compile preferences, from = " + p.getFrom() + ", to = " + p.getTo());
      e.printStackTrace();
    }
  }
}
