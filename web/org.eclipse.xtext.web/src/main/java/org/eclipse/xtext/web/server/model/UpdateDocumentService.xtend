/*******************************************************************************
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.web.server.model

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.validation.CheckMode
import org.eclipse.xtext.validation.IResourceValidator
import org.eclipse.xtext.web.server.InvalidRequestException

@Singleton
class UpdateDocumentService {
	
	@Inject IResourceValidator resourceValidator
	
	def updateFullText(XtextWebDocumentAccess document, String fullText, boolean reparse) throws InvalidRequestException {
		document.modify([ it, cancelIndicator |
			if (reparse) {
				dirty = true
				processingCompleted = false
				createNewStateId()
			}
			return new DocumentStateResult(stateId)
		], [ it, cancelIndicator |
			if (reparse) {
				text = fullText
			}
			processUpdatedDocument(cancelIndicator)
			return null
		])
	}
	
	def updateDeltaText(XtextWebDocumentAccess document, String deltaText, int offset, int replaceLength)
			throws InvalidRequestException {
		document.modify([ it, cancelIndicator |
			dirty = true
			processingCompleted = false
			createNewStateId()
			return new DocumentStateResult(stateId)
		], [ it, cancelIndicator |
			updateText(deltaText, offset, replaceLength)
			processUpdatedDocument(cancelIndicator)
			return null
		])
	}
	
	def void processUpdatedDocument(IXtextWebDocument it, CancelIndicator cancelIndicator) {
		if (!processingCompleted) {
			issues.addAll(resourceValidator.validate(resource, CheckMode.ALL, cancelIndicator))
			processingCompleted = true
		}
	}
	
}