package model.types;

import model.value.IValue;
import model.value.RefValue;

public class RefType implements IType{
    IType inner;

    public RefType(IType i)
    {
        this.inner = i;
    }
    public IType getInner()
    {
        return this.inner;
    }

    @Override
    public boolean equals(IType obj) {
        return obj instanceof RefType && ((RefType)obj).inner.equals(this.inner);
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, inner);
    }

    public String toString()
    {
        return "Ref" + "(" + inner.toString() + ")";
    }
}
