package de.zettsystems.java17;


public abstract sealed class SealedSuperclass implements SealedInterface permits FinalSubclass, NonSealedSubclass, SealedSubclass {

}
