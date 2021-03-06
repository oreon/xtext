/*******************************************************************************
 * Copyright (c) 2014 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtend.caliper.tests.clazz;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.Signature;
import org.eclipse.xtext.common.types.access.impl.URIHelperConstants;

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 * @since 2.5
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
class BinaryTypeSignature extends AbstractBinarySignature {
	
	protected int arrayDimensions = -1;
	
	BinaryTypeSignature(char[] chars) {
		super(chars);
//		checkInvariant();
	}
	
//	protected void checkInvariant() {
//		if (chars[offset + length - 1] == ';') {
//			throw new IllegalArgumentException();
//		}
//	}

	BinaryTypeSignature(char[] chars, int offset, int length) {
		super(chars, offset, length);
//		checkInvariant();
	}

	/**
	 * Returns the JvmType specific identifier of this signature, e.g.
	 * {@code java.lang.String} or {@code java.util.Map$Entry}.
	 */
	public String toIdentifier() {
		StringBuilder result = new StringBuilder(chars.length);
		toIdentifier(result, 0);
		return result.toString();
	}
	
	protected void toIdentifier(StringBuilder builder, int identifierFixup) {
		BinaryTypeSignature componentType = getArrayComponentType();
		if (componentType != this) {
			componentType.toIdentifier(builder, identifierFixup);
			int dim = getArrayDimensions();
			for(int i = 0; i < dim; i++) {
				builder.append("[]");
			}
		} else {
			if (length == 1) {
				switch(chars[offset]) {
					case 'B':
						builder.append("byte");
						return;
					case 'C':
						builder.append("char");
						return;
					case 'D':
						builder.append("double");
						return;
					case 'F':
						builder.append("float");
						return;
					case 'I':
						builder.append("int");
						return;
					case 'J':
						builder.append("long");
						return;
					case 'S':
						builder.append("short");
						return;
					case 'Z':
						builder.append("boolean");
						return;
					case 'V':
						builder.append("void");
						return;
				}
			}
			for(int i = offset + identifierFixup, max = offset + length - identifierFixup; i < max; i++) {
				char c = chars[i];
				if (c == '/') {
					builder.append('.');
				} else {
					builder.append(c);
				}
			}
		}
	}
	
	public int getArrayDimensions() {
		if (arrayDimensions == -1) {
			try {
				int count = 0;
				while (chars[count + offset] == Signature.C_ARRAY) {
					++count;
				}
				return arrayDimensions = count;
			} catch (ArrayIndexOutOfBoundsException e) { // signature is syntactically incorrect if last character is C_ARRAY
				throw new IllegalArgumentException();
			}
		}
		return arrayDimensions;
	}

	public BinaryTypeSignature getArrayComponentType() {
		int dimensions = getArrayDimensions();
		if (dimensions == 0) 
			return this;
		return new BinaryTypeSignature(chars, offset + dimensions, length - dimensions);
	}
	
	public List<BinaryTypeArgumentSignature> getTypeArguments() {
		return Collections.emptyList();
	}

	public BinaryTypeSignature getTypeErasure() {
		return this;
	}

	public URI getURI() {
		BinaryTypeSignature typeErasure = getArrayComponentType().getTypeErasure();
		switch(typeErasure.chars[typeErasure.offset]) {
			case 'B':
			case 'C':
			case 'D':
			case 'F':
			case 'I':
			case 'J':
			case 'S':
			case 'Z':
			case 'V': {
				return URIHelperConstants.PRIMITIVES_URI.appendFragment(toIdentifier());
			}
		}
		String identifier = typeErasure.toIdentifier();
		URI result = URIHelperConstants.OBJECTS_URI.appendSegment(identifier).appendFragment(toIdentifier());
		return result;
	}

	public String getTypeVariableName() {
		return null;
	}

	public BinarySimpleMemberSignature appendField(char[] fieldName) {
		return new BinarySimpleMemberSignature(this, fieldName, false);
	}

	public BinarySimpleMemberSignature appendMethod(char[] methodName) {
		return new BinarySimpleMemberSignature(this, methodName, true);
	}
	
}
