/*
 * generated by Xtext
 */
grammar PsiInternalBug287184TestLanguage;

options {
	superClass=AbstractPsiAntlrParser;
}

@lexer::header {
package org.eclipse.xtext.parser.assignments.idea.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

@parser::header {
package org.eclipse.xtext.parser.assignments.idea.parser.antlr.internal;

import org.eclipse.xtext.idea.parser.AbstractPsiAntlrParser;
import org.eclipse.xtext.parser.assignments.idea.lang.Bug287184TestLanguageElementTypeProvider;
import org.eclipse.xtext.idea.parser.TokenTypeProvider;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.assignments.services.Bug287184TestLanguageGrammarAccess;

import com.intellij.lang.PsiBuilder;
}

@parser::members {

	protected Bug287184TestLanguageGrammarAccess grammarAccess;

	protected Bug287184TestLanguageElementTypeProvider elementTypeProvider;

	public PsiInternalBug287184TestLanguageParser(PsiBuilder builder, TokenStream input, Bug287184TestLanguageElementTypeProvider elementTypeProvider, Bug287184TestLanguageGrammarAccess grammarAccess) {
		this(input);
		setPsiBuilder(builder);
    	this.grammarAccess = grammarAccess;
		this.elementTypeProvider = elementTypeProvider;
	}

	@Override
	protected String getFirstRuleName() {
		return "Model";
	}

}

//Entry rule entryRuleModel
entryRuleModel:
	{ markComposite(elementTypeProvider.getModelElementType()); }
	ruleModel
	EOF;

// Rule Model
ruleModel:
	(
		{
			markLeaf(elementTypeProvider.getModel_ModelKeyword_0ElementType());
		}
		otherlv_0='model'
		{
			doneLeaf(otherlv_0);
		}
		(
			(
				{
					markComposite(elementTypeProvider.getModel_NameFQNParserRuleCall_1_0ElementType());
				}
				lv_name_1_0=ruleFQN
				{
					doneComposite();
				}
			)
		)
		(
			(
				(
					{
						markComposite(elementTypeProvider.getModel_DetailDetailParserRuleCall_2_0_0ElementType());
					}
					lv_detail_2_1=ruleDetail
					{
						doneComposite();
					}
					    |
					{
						markComposite(elementTypeProvider.getModel_DetailAssociatedDetailParserRuleCall_2_0_1ElementType());
					}
					lv_detail_2_2=ruleAssociatedDetail
					{
						doneComposite();
					}
				)
			)
		)+
	)
;

//Entry rule entryRuleDetail
entryRuleDetail:
	{ markComposite(elementTypeProvider.getDetailElementType()); }
	ruleDetail
	EOF;

// Rule Detail
ruleDetail:
	(
		{
			markLeaf(elementTypeProvider.getDetail_DetailKeyword_0ElementType());
		}
		otherlv_0='detail'
		{
			doneLeaf(otherlv_0);
		}
		(
			(
				(
					{
						markLeaf(elementTypeProvider.getDetail_VisibilityPrivateKeyword_1_0_0ElementType());
					}
					lv_visibility_1_1='private'
					{
						doneLeaf(lv_visibility_1_1);
					}
					    |
					{
						markLeaf(elementTypeProvider.getDetail_VisibilityProtectedKeyword_1_0_1ElementType());
					}
					lv_visibility_1_2='protected'
					{
						doneLeaf(lv_visibility_1_2);
					}
					    |
					{
						markLeaf(elementTypeProvider.getDetail_VisibilityPublicKeyword_1_0_2ElementType());
					}
					lv_visibility_1_3='public'
					{
						doneLeaf(lv_visibility_1_3);
					}
				)
			)
		)?
		(
			(
				{
					markComposite(elementTypeProvider.getDetail_DetailClassModelCrossReference_2_0ElementType());
				}
				ruleFQN
				{
					doneComposite();
				}
			)
		)
	)
;

//Entry rule entryRuleAssociatedDetail
entryRuleAssociatedDetail:
	{ markComposite(elementTypeProvider.getAssociatedDetailElementType()); }
	ruleAssociatedDetail
	EOF;

// Rule AssociatedDetail
ruleAssociatedDetail:
	(
		{
			markLeaf(elementTypeProvider.getAssociatedDetail_AssociatedKeyword_0ElementType());
		}
		otherlv_0='associated'
		{
			doneLeaf(otherlv_0);
		}
		(
			(
				{
					markComposite(elementTypeProvider.getAssociatedDetail_DetailClassModelCrossReference_1_0ElementType());
				}
				ruleFQN
				{
					doneComposite();
				}
			)
		)
		{
			markLeaf(elementTypeProvider.getAssociatedDetail_SemicolonKeyword_2ElementType());
		}
		otherlv_2=';'
		{
			doneLeaf(otherlv_2);
		}
	)
;

//Entry rule entryRuleFQN
entryRuleFQN:
	{ markComposite(elementTypeProvider.getFQNElementType()); }
	ruleFQN
	EOF;

// Rule FQN
ruleFQN:
	(
		{
			markLeaf(elementTypeProvider.getFQN_IDTerminalRuleCall_0ElementType());
		}
		this_ID_0=RULE_ID
		{
			doneLeaf(this_ID_0);
		}
		(
			{
				markLeaf(elementTypeProvider.getFQN_FullStopKeyword_1_0ElementType());
			}
			kw='.'
			{
				doneLeaf(kw);
			}
			{
				markLeaf(elementTypeProvider.getFQN_IDTerminalRuleCall_1_1ElementType());
			}
			this_ID_2=RULE_ID
			{
				doneLeaf(this_ID_2);
			}
		)*
	)
;

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'|'\'' ('\\' .|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;
