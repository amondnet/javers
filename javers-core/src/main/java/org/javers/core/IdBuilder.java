package org.javers.core;

import org.javers.common.validation.Validate;
import org.javers.core.metamodel.object.GlobalIdFactory;
import org.javers.core.metamodel.object.InstanceId;
import org.javers.core.metamodel.object.ValueObjectId;

/**
 * @author bartosz walacik
 */
public class IdBuilder {
    private final GlobalIdFactory globalIdFactory;

    private InstanceId owner;

    public IdBuilder(GlobalIdFactory globalIdFactory) {
        this.globalIdFactory = globalIdFactory;
    }

    public IdBuilder withOwner(Object localId, Class ownerEntityClass) {
        owner = instanceId(localId, ownerEntityClass);
        return this;
    }

    public ValueObjectId voId(Class valueObjectClass, String path){
        Validate.conditionFulfilled(owner != null, "call withOwner() first");
        return globalIdFactory.createFromPath(owner, valueObjectClass, path);
    }

    public InstanceId instanceId(Object instance){
        Validate.argumentsAreNotNull(instance);

        return (InstanceId)globalIdFactory.createId(instance, null);
    }

    public InstanceId instanceId(Object localId, Class entityClass){
        Validate.argumentsAreNotNull(localId, entityClass);
        return  globalIdFactory.createFromId(localId, entityClass);
    }
}
