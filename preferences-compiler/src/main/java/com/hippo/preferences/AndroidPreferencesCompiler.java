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

import com.hippo.preferences.annotation.Items;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.forge.roaster.model.source.ParameterSource;
import org.jboss.forge.roaster.model.util.Types;

public class AndroidPreferencesCompiler {

  private static final String DEFAULT_GET_PREFIX = "get";
  private static final String DEFAULT_PUT_PREFIX = "put";

  public static void compile(File in, File out, String name) throws IOException {
    JavaClassSource inClass = Roaster.parse(JavaClassSource.class, in);
    JavaClassSource outClass = Roaster.create(JavaClassSource.class);

    // Init out class
    String[] pn = parseClassName(name);
    outClass.setPackage(pn[0]);
    outClass.setName(pn[1]);
    outClass.extendSuperType(inClass);

    // Check whether same package
    boolean samePackage = equals(inClass.getPackage(), outClass.getPackage());

    // Add constructor
    for (MethodSource<JavaClassSource> m: inClass.getMethods()) {
      if (!m.isConstructor()
          || m.isPrivate()
          || (!samePackage && m.isPackagePrivate())) {
        continue;
      }

      MethodSource<JavaClassSource> c = outClass.addMethod();
      c.setConstructor(true);
      c.setPublic();
      List<ParameterSource<JavaClassSource>> ps = m.getParameters();
      for (ParameterSource<JavaClassSource> p: ps) {
        Type<JavaClassSource> type = p.getType();

        // Get generics import
        String[] generics = Types.splitGenerics(type.toString());
        for (String g: generics) {
          String n = inClass.resolveType(g);
          if (needImport(n, outClass.getPackage())) {
            outClass.addImport(n);
          }
        }

        c.addParameter(p.getType().getQualifiedNameWithGenerics(), p.getName());
      }
      c.setBody(buildConstructorBody(ps));
    }

    // Export annotation
    AnnotationSource<JavaClassSource> annotations = inClass.getAnnotation(Items.class);
    if (annotations != null) {
      String getPrefix = annotations.getStringValue("getPrefix");
      String putPrefix = annotations.getStringValue("putPrefix");
      if (getPrefix == null) {
        getPrefix = DEFAULT_GET_PREFIX;
      }
      if (putPrefix == null) {
        putPrefix = DEFAULT_PUT_PREFIX;
      }

      addAccess(outClass, annotations, "booleanItems", boolean.class, putPrefix, "putBoolean", getPrefix, "getBoolean");
      addAccess(outClass, annotations, "intItems", int.class, putPrefix, "putInt", getPrefix, "getInt");
      addAccess(outClass, annotations, "longItems", long.class, putPrefix, "putLong", getPrefix, "getLong");
      addAccess(outClass, annotations, "floatItems", float.class, putPrefix, "putFloat", getPrefix, "getFloat");
      addAccess(outClass, annotations, "stringItems", String.class, putPrefix, "putString", getPrefix, "getString");
      addAccess(outClass, annotations, "decimalIntItems", int.class, putPrefix, "putDecimalInt", getPrefix, "getDecimalInt");
    }

    FileWriter fileWriter = new FileWriter(out);
    fileWriter.write(outClass.toString());
    fileWriter.close();
  }

  // Return a String array of package and simple name
  private static String[] parseClassName(String name) {
    int index = name.lastIndexOf('.');
    if (index != -1) {
      return new String[] {name.substring(0, index), name.substring(index + 1)};
    } else {
      return new String[] {null, name};
    }
  }

  // Return true if need add import
  private static boolean needImport(String n, String p) {
    return !inPackage(n, "java.lang") && !inPackage(n, p) && n.contains(".");
  }

  // Return true if the full class name is in the package
  private static boolean inPackage(String n, String p) {
    return n.startsWith(p)
        && n.charAt(p.length()) == '.'
        && n.indexOf('.', p.length() + 1) == -1;
  }

  private static String buildConstructorBody(List<ParameterSource<JavaClassSource>> ps) {
    StringBuilder sb = new StringBuilder();
    sb.append("super(");
    boolean first = true;
    for (ParameterSource<JavaClassSource> p: ps) {
      if (first) {
        first = false;
      } else {
        sb.append(", ");
      }
      sb.append(p.getName());
    }
    sb.append(");");
    return sb.toString();
  }

  private static GeneralItem parseItem(AnnotationSource<JavaClassSource> item) {
    String name = item.getStringValue("name");
    String key = item.getStringValue("key");
    String value = item.getLiteralValue("defValue");

    if (isEmpty(key) || isEmpty(value)) {
      return null;
    }

    if (isEmpty(name)) {
      name = keyToName(key);
    }

    GeneralItem i = new GeneralItem();
    i.name = name;
    i.key = key;
    i.value = value;
    return i;
  }

  // hello_world_ha_ha -> helloWorldHaHa
  private static String keyToName(String k) {
    StringBuilder sb = new StringBuilder();
    boolean after = false;
    for (int i = 0, n = k.length(); i < n; ++i) {
      char c = k.charAt(i);
      if (c == '_') {
        after = true;
      } else if (after) {
        after = false;
        sb.append(Character.toUpperCase(c));
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  // prefix: sleep
  // name: fish
  // -> sleepFish
  private static String methodName(String prefix, String name) {
    if (isEmpty(prefix)) {
      return prefix;
    } else {
      return prefix + capitalize(name);
    }
  }

  private static void addAccess(JavaClassSource clazz,
      AnnotationSource<JavaClassSource> annotation, String fieldName,
      Class type, String putPrefix, String putMethod, String getPrefix, String getMethod) {
    AnnotationSource<JavaClassSource>[] items =
        annotation.getAnnotationArrayValue(fieldName);
    if (items != null) {
      for (AnnotationSource<JavaClassSource> i: items) {
        GeneralItem item = parseItem(i);
        if (item == null) {
          continue;
        }
        addPut(clazz, item, type, putPrefix, putMethod);
        addGet(clazz, item, type, getPrefix, getMethod);
      }
    }
  }

  private static void addPut(JavaClassSource clazz, GeneralItem item,
      Class type, String prefix, String method) {
    MethodSource<JavaClassSource> c = clazz.addMethod();
    c.setConstructor(false);
    c.setPublic();
    c.setName(methodName(prefix, item.name));
    c.addParameter(type, "value");
    c.setReturnType(void.class);
    c.setBody(method + "(\"" + item.key + "\", value);");
  }

  private static void addGet(JavaClassSource clazz, GeneralItem item,
      Class type, String prefix, String method) {
    MethodSource<JavaClassSource> c = clazz.addMethod();
    c.setConstructor(false);
    c.setPublic();
    c.setName(methodName(prefix, item.name));
    c.setReturnType(type);
    c.setBody("return " + method + "(\"" + item.key + "\", " + item.value + ");");
  }

  private static boolean isEmpty(String s) {
    return s == null || s.isEmpty();
  }

  private static String capitalize(String s) {
    if (isEmpty(s)) {
      return s;
    }
    return Character.toUpperCase(s.charAt(0)) + s.substring(1);
  }

  private static boolean equals(Object a, Object b) {
    return (a == b) || (a != null && a.equals(b));
  }

  private static class GeneralItem {
    public String name;
    public String value;
    public String key;
  }
}
