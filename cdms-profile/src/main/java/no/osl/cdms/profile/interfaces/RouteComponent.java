
package no.osl.cdms.profile.interfaces;

import org.springframework.stereotype.Component;


@Component
public interface RouteComponent<S, T> {
    public T process(S s);
}
