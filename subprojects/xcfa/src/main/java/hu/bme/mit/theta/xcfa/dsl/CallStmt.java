package hu.bme.mit.theta.xcfa.dsl;

import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.stmt.xcfa.XcfaCallStmt;
import hu.bme.mit.theta.xcfa.XCFA;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;

public class CallStmt extends XcfaCallStmt {
	private final VarDecl<?> var;
	private final boolean isVoid;
	private final List<VarDecl<?>> params;
	private XCFA.Process.Procedure procedure;

	CallStmt(VarDecl<?> var, XCFA.Process.Procedure procedure, List<VarDecl<?>> params) {
		this.var = var;
		isVoid = var == null;
		this.procedure = procedure;
		this.params = params;
	}

	public boolean isVoid() {
		return isVoid;
	}

	public VarDecl<?> getVar() {
		return var;
	}

	public List<VarDecl<?>> getParams() {
		return params;
	}

	public XCFA.Process.Procedure getProcedure() {
		return procedure;
	}

	void setProcedure(XCFA.Process.Procedure procedure) {
		checkState(this.procedure == null);
		this.procedure = procedure;
	}
}
