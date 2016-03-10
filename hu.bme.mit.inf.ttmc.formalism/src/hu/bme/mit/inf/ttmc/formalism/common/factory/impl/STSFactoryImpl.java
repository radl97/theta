package hu.bme.mit.inf.ttmc.formalism.common.factory.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;

import hu.bme.mit.inf.ttmc.constraint.expr.Expr;
import hu.bme.mit.inf.ttmc.constraint.type.Type;
import hu.bme.mit.inf.ttmc.formalism.common.decl.VarDecl;
import hu.bme.mit.inf.ttmc.formalism.common.decl.impl.VarDeclImpl;
import hu.bme.mit.inf.ttmc.formalism.common.expr.PrimedExpr;
import hu.bme.mit.inf.ttmc.formalism.common.expr.impl.PrimedExprImpl;
import hu.bme.mit.inf.ttmc.formalism.common.factory.STSFactory;

public class STSFactoryImpl implements STSFactory {
	private final HashMap<String, VarDecl<?>> nameToVar;
	
	public STSFactoryImpl() {
		nameToVar = new HashMap<>();
	}

	@Override
	public <T extends Type> VarDecl<T> Var(String name, T type) {
		checkNotNull(name);
		checkNotNull(type);
		checkArgument(name.length() > 0);
		checkArgument(nameToVar.get(name) == null);
		
		final VarDecl<T> varDecl = new VarDeclImpl<>(name, type);
		nameToVar.put(name, varDecl);
		return varDecl;
	}

	@Override
	public <T extends Type> PrimedExpr<T> Prime(Expr<? extends T> op) {
		checkNotNull(op);
		return new PrimedExprImpl<>(op);
	}

}
