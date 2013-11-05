/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.groovy;

import groovy.text.GStringTemplateEngine;
import groovy.text.Template;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;

/**
 * @author Dave Syer
 */
public abstract class GroovyTemplate {

	public static String template(String name) throws IOException,
			CompilationFailedException, ClassNotFoundException {
		return template(name, Collections.<String, Object> emptyMap());
	}

	public static String template(String name, Map<String, ?> model) throws IOException,
			CompilationFailedException, ClassNotFoundException {
		return getTemplate(name).make(model).toString();
	}

	private static Template getTemplate(String name) throws CompilationFailedException,
			ClassNotFoundException, IOException {
		GStringTemplateEngine engine = new GStringTemplateEngine();

		File file = new File("templates", name);
		if (file.exists()) {
			return engine.createTemplate(file);
		}

		ClassLoader classLoader = GroovyTemplate.class.getClassLoader();
		URL resource = classLoader.getResource("templates/" + name);
		if (resource != null) {
			return engine.createTemplate(resource);
		}

		return engine.createTemplate(name);
	}

}