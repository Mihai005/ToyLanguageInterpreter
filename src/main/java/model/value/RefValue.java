package model.value;

import model.types.*;

public class RefValue implements IValue{
    int address;
    IType locationType;

    public RefValue(int address, IType type)
    {
        this.address = address;
        this.locationType = type;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    public int getAddr()
    {
        return address;
    }

    public IType getLocationType()
    {
        return this.locationType;
    }

    public boolean equals(Object obj)
    {
        return obj instanceof RefValue && ((RefValue)obj).getLocationType().equals(this.locationType) &&
                ((RefValue)obj).getAddr() == this.address;
    }
    public String toString()
    {
        return this.locationType.toString();
    }
}
