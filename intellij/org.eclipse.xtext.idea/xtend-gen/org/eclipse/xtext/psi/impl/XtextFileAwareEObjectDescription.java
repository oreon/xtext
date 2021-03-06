/**
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.xtext.psi.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend.lib.annotations.Delegate;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author kosyakov - Initial contribution and API
 */
@FinalFieldsConstructor
@SuppressWarnings("all")
public class XtextFileAwareEObjectDescription implements IEObjectDescription {
  @Accessors
  private final BaseXtextFile xtextFile;
  
  @Delegate
  private final IEObjectDescription description;
  
  @Override
  public String toString() {
    String _simpleName = XtextFileAwareEObjectDescription.class.getSimpleName();
    String _plus = (_simpleName + ":");
    QualifiedName _qualifiedName = this.description.getQualifiedName();
    String _plus_1 = (_plus + _qualifiedName);
    String _plus_2 = (_plus_1 + ":");
    return (_plus_2 + this.description);
  }
  
  public XtextFileAwareEObjectDescription(final BaseXtextFile xtextFile, final IEObjectDescription description) {
    super();
    this.xtextFile = xtextFile;
    this.description = description;
  }
  
  @Pure
  public BaseXtextFile getXtextFile() {
    return this.xtextFile;
  }
  
  public EClass getEClass() {
    return this.description.getEClass();
  }
  
  public EObject getEObjectOrProxy() {
    return this.description.getEObjectOrProxy();
  }
  
  public URI getEObjectURI() {
    return this.description.getEObjectURI();
  }
  
  public QualifiedName getName() {
    return this.description.getName();
  }
  
  public QualifiedName getQualifiedName() {
    return this.description.getQualifiedName();
  }
  
  public String getUserData(final String key) {
    return this.description.getUserData(key);
  }
  
  public String[] getUserDataKeys() {
    return this.description.getUserDataKeys();
  }
}
