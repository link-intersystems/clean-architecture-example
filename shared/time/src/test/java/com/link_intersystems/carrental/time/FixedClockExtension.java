package com.link_intersystems.carrental.time;

import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

class FixedClockExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(FixedClockExtension.class);
    public static final String PREVIOUS_CLOCK_KEY = "clock";
    public static final String FIXED_CLOCK_KEY = "fixedClock";

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Optional<FixedClock> fixedClock = getFixedClockAnnotation(extensionContext);
        fixedClock.ifPresent(fc -> setClock(fc, extensionContext));
    }

    private void setClock(FixedClock fixedClock, ExtensionContext extensionContext) {
        ExtensionContext.Store store = extensionContext.getStore(NAMESPACE);
        Clock actualClock = ClockProvider.getClock();
        store.put(PREVIOUS_CLOCK_KEY, actualClock);

        String value = fixedClock.value();
        LocalDateTime localDateTime = LocalDateTime.parse(value.replaceAll(" ", "T"));

        String zoneIdValue = fixedClock.zoneId();
        ZoneId zoneId = "null".equals(zoneIdValue) ? ZoneId.systemDefault() : ZoneId.of(zoneIdValue);

        Clock clockForTest = LocalDateTimeUtils.clockOf(localDateTime, zoneId);
        ClockProvider.setClock(clockForTest);

        store.put(FIXED_CLOCK_KEY, clockForTest);
    }

    private Optional<FixedClock> getFixedClockAnnotation(ExtensionContext extensionContext) {
        Optional<Method> testMethod = extensionContext.getTestMethod();
        Optional<FixedClock> fixedClock = testMethod.map(this::getFixedClockAnnotation);
        Class<?> requiredTestClass = extensionContext.getRequiredTestClass();
        return fixedClock.or(() -> getFixedClockAnnotation(requiredTestClass));
    }

    private FixedClock getFixedClockAnnotation(Method method) {
        return method.getDeclaredAnnotation(FixedClock.class);
    }

    private Optional<FixedClock> getFixedClockAnnotation(Class<?> aClass) {
        if (aClass == null) {
            return Optional.empty();
        }

        Optional<FixedClock> fixedClock = Optional.ofNullable(aClass.getDeclaredAnnotation(FixedClock.class));

        return fixedClock.or(() -> getFixedClockAnnotation(aClass.getSuperclass()));
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        ExtensionContext.Store store = extensionContext.getStore(NAMESPACE);
        Clock clock = store.get(PREVIOUS_CLOCK_KEY, Clock.class);
        if (clock != null) {
            ClockProvider.setClock(clock);
        }

        store.remove(PREVIOUS_CLOCK_KEY);
        store.remove(FIXED_CLOCK_KEY);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        Class<?> parmeterType = parameter.getType();
        return Clock.class.equals(parmeterType) || MutableClock.class.equals(parmeterType);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        if (supportsParameter(parameterContext, extensionContext)) {
            Parameter parameter = parameterContext.getParameter();
            Class<?> parameterType = parameter.getType();
            if (Clock.class.equals(parameterType)) {
                ExtensionContext.Store store = extensionContext.getStore(NAMESPACE);
                return store.get(FIXED_CLOCK_KEY);
            } else if (MutableClock.class.equals(parameterType)) {
                return (MutableClock) ldt -> ClockProvider.setClock(LocalDateTimeUtils.clockOf(ldt));
            }
        }
        return null;
    }
}
