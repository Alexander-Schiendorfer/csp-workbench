package hgb.csp.mvc;

/**
 * Defines the basic methods that a subject
 * in the csp-mvc structure has to provide
 *
 * Use SubjectImpl and delegation
 * @author Alexander Schiendorfer
 */
public interface Subject {
	void addObserver(Object observer);
        void removeObserver(Object observer);
        void removeAllObservers();
}
