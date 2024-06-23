package net.spaceeye.vmod.mixin;


//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.ObjectCodec;
//import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
//import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Redirect;
//
//import java.util.Map;
//
//import static net.spaceeye.vmod.VMKt.ELOG;

//TODO didn't work
//@Mixin(targets = {"org.valkyrienskies.core.impl.util.serialization.GuaveSerializationModule$ClassToInstanceMapDeserializer"})
public class GuaveSerializationModuleMixin {
//    @WrapOperation(method = "deserialize(Lcom/fasterxml/jackson/core/JsonParser;Lcom/fasterxml/jackson/databind/DeserializationContext;)Lcom/google/common/collect/MutableClassToInstanceMap;",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Ljava/lang/Class;forName(Ljava/lang/String;)Ljava/lang/Class;"
//            ),
//            remap = false)
//    Class<?> dontFuckingCrashTheGameFirst(String className, Operation<Class<?>> original) {
//        ELOG("HERE 1 " + className);
//        try {
//            return original.call(className);
//        } catch (Exception e) {
//            ELOG("VS has failed to deserialize class with error:\n" + e.getMessage());
//            return null;
//        }
//    }
//    @WrapOperation(method = "deserialize(Lcom/fasterxml/jackson/core/JsonParser;Lcom/fasterxml/jackson/databind/DeserializationContext;)Lcom/google/common/collect/MutableClassToInstanceMap;",
//    at = @At(
//            value = "INVOKE",
//            target = "Lcom/fasterxml/jackson/core/ObjectCodec;readValue(Lcom/fasterxml/jackson/core/JsonParser;Ljava/lang/Class;)Ljava/lang/Object;"
//    ),
//    remap = false)
//    <T> T dontFuckingCrashTheGameSecond(ObjectCodec instance, JsonParser jsonParser, Class<T> tClass, Operation<T> original) {
//        try {
//            return original.call(instance, jsonParser, tClass);
//        } catch (Exception e) {
//            ELOG("VS has failed to deserialize class with error:\n" + e.getMessage());
//            return null;
//        }
//    }
//
//    @Redirect(method = "deserialize(Lcom/fasterxml/jackson/core/JsonParser;Lcom/fasterxml/jackson/databind/DeserializationContext;)Lcom/google/common/collect/MutableClassToInstanceMap;",
//    at = @At(
//            value = "INVOKE",
//            target = "Lkotlin/jvm/internal/Intrinsics;checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V",
//            ordinal = 0
//    ),
//    remap = false)
//    void cancelCheckNotNull(Object value, String expression) {}
//
//    @WrapOperation(method = "deserialize(Lcom/fasterxml/jackson/core/JsonParser;Lcom/fasterxml/jackson/databind/DeserializationContext;)Lcom/google/common/collect/MutableClassToInstanceMap;",
//    at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"),
//    remap = false)
//    <K, V> Object dontFuckingCrashTheGameThird(Map instance, K k, V v, Operation<V> original) {
//        ELOG("HERE 4");
//        if (v == null) {return null;}
//        ELOG("HERE 5");
//        return original.call(instance, k, v);
//    }
}
