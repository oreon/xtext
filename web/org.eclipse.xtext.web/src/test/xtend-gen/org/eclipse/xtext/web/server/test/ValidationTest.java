/**
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.web.server.test;

import java.io.File;
import java.util.Collections;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.web.server.IServiceResult;
import org.eclipse.xtext.web.server.ServiceConflictResult;
import org.eclipse.xtext.web.server.XtextServiceDispatcher;
import org.eclipse.xtext.web.server.test.AbstractWebServerTest;
import org.eclipse.xtext.web.server.test.HashMapSessionStore;
import org.eclipse.xtext.web.server.validation.ValidationResult;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Pair;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class ValidationTest extends AbstractWebServerTest {
  protected void assertValidationResult(final String resourceContent, final String expectedResult) {
    try {
      final HashMapSessionStore sessionStore = new HashMapSessionStore();
      XtextServiceDispatcher _dispatcher = this.getDispatcher();
      Pair<String, String> _mappedTo = Pair.<String, String>of("fullText", resourceContent);
      final XtextServiceDispatcher.ServiceDescriptor validate = _dispatcher.getService("/validation", Collections.<String, String>unmodifiableMap(CollectionLiterals.<String, String>newHashMap(_mappedTo)), sessionStore);
      boolean _isHasSideEffects = validate.isHasSideEffects();
      Assert.assertFalse(_isHasSideEffects);
      boolean _isHasTextInput = validate.isHasTextInput();
      Assert.assertTrue(_isHasTextInput);
      Function0<? extends IServiceResult> _service = validate.getService();
      IServiceResult _apply = _service.apply();
      final ValidationResult result = ((ValidationResult) _apply);
      String _string = result.toString();
      Assert.assertEquals(expectedResult, _string);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testSyntaxError() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ValidationResult [");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("entries = ArrayList (");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("Entry [");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("description = \"missing EOF at \'stat\'\"");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("severity = \"error\"");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("line = 1");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("startOffset = 0");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("endOffset = 4");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("  ");
    _builder.append(")");
    _builder.newLine();
    _builder.append("]");
    this.assertValidationResult("stat foo end", _builder.toString());
  }
  
  @Test
  public void testCrossRefError() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ValidationResult [");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("entries = ArrayList (");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("Entry [");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("description = \"Couldn\'t resolve reference to Signal \'x\'.\"");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("severity = \"error\"");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("line = 1");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("startOffset = 14");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("endOffset = 15");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("  ");
    _builder.append(")");
    _builder.newLine();
    _builder.append("]");
    this.assertValidationResult("state foo set x = true end", _builder.toString());
  }
  
  @Test
  public void testCustomValidationError() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ValidationResult [");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("entries = ArrayList (");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("Entry [");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("description = \"Only output signals are allowed for write access.\"");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("severity = \"error\"");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("line = 1");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("startOffset = 29");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("endOffset = 30");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("  ");
    _builder.append(")");
    _builder.newLine();
    _builder.append("]");
    this.assertValidationResult("input signal x state foo set x = true end", _builder.toString());
  }
  
  @Test
  public void testValidateFile() {
    try {
      final File file = this.createFile("stat foo end");
      final HashMapSessionStore sessionStore = new HashMapSessionStore();
      XtextServiceDispatcher _dispatcher = this.getDispatcher();
      String _name = file.getName();
      Pair<String, String> _mappedTo = Pair.<String, String>of("resource", _name);
      final XtextServiceDispatcher.ServiceDescriptor validate = _dispatcher.getService("/validation", Collections.<String, String>unmodifiableMap(CollectionLiterals.<String, String>newHashMap(_mappedTo)), sessionStore);
      boolean _isHasSideEffects = validate.isHasSideEffects();
      Assert.assertFalse(_isHasSideEffects);
      boolean _isHasTextInput = validate.isHasTextInput();
      Assert.assertFalse(_isHasTextInput);
      Function0<? extends IServiceResult> _service = validate.getService();
      IServiceResult _apply = _service.apply();
      final ValidationResult result = ((ValidationResult) _apply);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("ValidationResult [");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("entries = ArrayList (");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("Entry [");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("description = \"missing EOF at \'stat\'\"");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("severity = \"error\"");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("line = 1");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("startOffset = 0");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("endOffset = 4");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("]");
      _builder.newLine();
      _builder.append("  ");
      _builder.append(")");
      _builder.newLine();
      _builder.append("]");
      final String expectedResult = _builder.toString();
      String _string = result.toString();
      Assert.assertEquals(expectedResult, _string);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Test
  public void testIncorrectStateId() {
    try {
      final File file = this.createFile("state foo end");
      final HashMapSessionStore sessionStore = new HashMapSessionStore();
      XtextServiceDispatcher _dispatcher = this.getDispatcher();
      String _name = file.getName();
      Pair<String, String> _mappedTo = Pair.<String, String>of("resource", _name);
      Pair<String, String> _mappedTo_1 = Pair.<String, String>of("requiredStateId", "totalerquatsch");
      final XtextServiceDispatcher.ServiceDescriptor validate = _dispatcher.getService("/validation", Collections.<String, String>unmodifiableMap(CollectionLiterals.<String, String>newHashMap(_mappedTo, _mappedTo_1)), sessionStore);
      boolean _isHasConflict = validate.isHasConflict();
      Assert.assertTrue(_isHasConflict);
      Function0<? extends IServiceResult> _service = validate.getService();
      final IServiceResult result = _service.apply();
      Matcher<IServiceResult> _instanceOf = IsInstanceOf.<IServiceResult>instanceOf(ServiceConflictResult.class);
      Assert.<IServiceResult>assertThat(result, _instanceOf);
      String _conflict = ((ServiceConflictResult) result).getConflict();
      Assert.assertEquals(_conflict, "invalidStateId");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
