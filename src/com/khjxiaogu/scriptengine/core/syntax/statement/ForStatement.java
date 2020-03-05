package com.khjxiaogu.scriptengine.core.syntax.statement;

import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.ParseReader;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.SyntaxError;
import com.khjxiaogu.scriptengine.core.syntax.Block;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlock;
import com.khjxiaogu.scriptengine.core.syntax.CodeBlockAttribute;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.StatementParser;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;

public class ForStatement implements Block {

	private CodeNode Init;
	private CodeNode Cond;
	private CodeNode Incr;
	private CodeNode Body;

	public ForStatement() {
	}

	@Override
	public CodeNode parse(ParseReader reader) throws KSException {
		StatementParser parser = new StatementParser();
		while (true) {
			if (!reader.has()) {
				break;
			}
			char c = reader.read();
			//
			while (Character.isWhitespace(c)) {
				c = reader.eat();
			}
			if (!reader.has()) {
				break;
			}
			if (c == '(' && Init == null) {
				parser.clear();
				c = reader.eat();
				//System.out.println(c);
				Init = parser.parseUntil(reader, ';');
				//System.out.println(reader.read());
				reader.eat();
				Cond = parser.parseUntil(reader, ';');
				reader.eat();
				Incr = parser.parseUntil(reader, ')');
				reader.eat();
			} else if (c == '{') {
				if (Incr == null)
					throw new SyntaxError("错误的for表达式");
				c = reader.eat();
				Body = new CodeBlock(CodeBlockAttribute.BREAKABLE).parse(reader);
				break;
			} else if (Incr != null) {
				parser.clear();
				Body = parser.parseUntilOrBlock(reader, ';');
				//reader.eat();
				break;
			}
		}
		if (Init == null || Cond == null || Incr == null || Body == null)
			throw new SyntaxError("错误的for表达式");
		return this;
	}

	@Override
	public KVariant eval(KEnvironment env) throws KSException {
		for (Init.eval(env); Cond.eval(env).asBoolean(); Incr.eval(env)) {
			Body.eval(env);
		}
		return null;
	}

	@Override
	public String toString() {
		return "for(" + Init.toString() + ";" + Cond.toString() + ";" + Incr.toString() + ")\n" + Body.toString();
	}

	@Override
	public void Visit(List<String> parentMap) throws KSException {
		Visitable.Visit(Init, parentMap);
		Visitable.Visit(Cond, parentMap);
		Visitable.Visit(Incr, parentMap);
		Visitable.Visit(Body, parentMap);
	}

	@Override
	public void init(KEnvironment env) throws KSException {
	}
}