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

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.AppPlugin;
import com.android.build.gradle.LibraryExtension;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.api.BaseVariant;
import com.android.build.gradle.api.LibraryVariant;
import com.android.builder.model.SourceProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class PreferencesPlugin implements Plugin<Project> {

  @Override
  public void apply(final Project project) {
    if (project.getPlugins().hasPlugin(AppPlugin.class)) {
      AppExtension android = project.getExtensions().getByType(AppExtension.class);
      android.getApplicationVariants().all(new Action<ApplicationVariant>() {
        @Override
        public void execute(ApplicationVariant variant) {
          applyPreferencesTask(project, variant);
        }
      });
    } else {
      LibraryExtension android = project.getExtensions().getByType(LibraryExtension.class);
      android.getLibraryVariants().all(new Action<LibraryVariant>() {
        @Override
        public void execute(LibraryVariant variant) {
          applyPreferencesTask(project, variant);
        }
      });
    }
  }

  public void applyPreferencesTask(Project project, BaseVariant variant) {
    String taskName = "generate" + capitalize(variant.getName()) + "Preferences";
    File outputDir = project.file(
        project.getBuildDir() + "/generated/source/preferences/" + variant.getName());

    PreferencesTask task = project.getTasks().create(taskName, PreferencesTask.class);
    task.setOutputDir(outputDir);
    task.setSourceDirs(sourceDirs(variant));

    variant.registerJavaGeneratingTask(task, outputDir);
  }

  private static Collection<File> sourceDirs(BaseVariant variant) {
    List<File> dir = new ArrayList<>();
    for (SourceProvider source: variant.getSourceSets()) {
      dir.addAll(source.getJavaDirectories());
    }
    return dir;
  }

  private static String capitalize(String s) {
    if (s == null || s.isEmpty()) {
      return s;
    }
    return Character.toUpperCase(s.charAt(0)) + s.substring(1);
  }
}
