package com.khjxiaogu.scriptengine.core.syntax.operator.p00;

import java.util.List;

import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.ASTParser;
import com.khjxiaogu.scriptengine.core.syntax.Block;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;

public class Case implements CodeNode, ASTParser, Block {
	private CodeNode cond;

	public CodeNode getCond() {
		return cond;
	}

	public Case() {
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		StatementParser parser = new StatementParser();
		cond = parser.parseUntilOrBlock(reader, ':');
		return this;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		return null;
	}

	@Override
	public void Visit(List<String> parentMap) {
		Visitable.Visit(cond, parentMap);
	}

	@Override
	public void init(KEnvironment env) throws KSException {

	}

	@Override
	public String toString() {
		return "case" + cond.toString() + ":";
	}

}
