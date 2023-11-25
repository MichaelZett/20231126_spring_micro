package de.zettsystems.java17;

/**
 Wenn die erlaubten Subtypen alle in der gleichen compilation unit wie die sealed Klasse deklariert sind (gleiches source file),
 kann die permits-Klausel weggelassen werden und es wird gefolgert, dass alle Subtypen in der gleichen compilation unit sind.
 Subtypen müssen alle im gleichen package sein.
 */
public sealed interface SealedInterface permits SealedInterfaceOnly, SealedRecord, SealedSuperclass {
}
