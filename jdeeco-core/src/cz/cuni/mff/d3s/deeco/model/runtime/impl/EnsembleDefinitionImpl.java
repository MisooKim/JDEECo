/**
 */
package cz.cuni.mff.d3s.deeco.model.runtime.impl;

import cz.cuni.mff.d3s.deeco.model.runtime.api.Condition;
import cz.cuni.mff.d3s.deeco.model.runtime.api.EnsembleDefinition;
import cz.cuni.mff.d3s.deeco.model.runtime.api.Exchange;
import cz.cuni.mff.d3s.deeco.model.runtime.api.Trigger;
import cz.cuni.mff.d3s.deeco.model.runtime.meta.RuntimeMetadataPackage;
import cz.cuni.mff.d3s.deeco.network.CommunicationBoundaryPredicate;
import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ensemble Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link cz.cuni.mff.d3s.deeco.model.runtime.impl.EnsembleDefinitionImpl#getName <em>Name</em>}</li>
 *   <li>{@link cz.cuni.mff.d3s.deeco.model.runtime.impl.EnsembleDefinitionImpl#getMembership <em>Membership</em>}</li>
 *   <li>{@link cz.cuni.mff.d3s.deeco.model.runtime.impl.EnsembleDefinitionImpl#getKnowledgeExchange <em>Knowledge Exchange</em>}</li>
 *   <li>{@link cz.cuni.mff.d3s.deeco.model.runtime.impl.EnsembleDefinitionImpl#getTriggers <em>Triggers</em>}</li>
 *   <li>{@link cz.cuni.mff.d3s.deeco.model.runtime.impl.EnsembleDefinitionImpl#getCommunicationBoundary <em>Communication Boundary</em>}</li>
 *   <li>{@link cz.cuni.mff.d3s.deeco.model.runtime.impl.EnsembleDefinitionImpl#getPartitionedBy <em>Partitioned By</em>}</li>
 *   <li>{@link cz.cuni.mff.d3s.deeco.model.runtime.impl.EnsembleDefinitionImpl#getCoordinatorRole <em>Coordinator Role</em>}</li>
 *   <li>{@link cz.cuni.mff.d3s.deeco.model.runtime.impl.EnsembleDefinitionImpl#getMemberRole <em>Member Role</em>}</li>
 *   <li>{@link cz.cuni.mff.d3s.deeco.model.runtime.impl.EnsembleDefinitionImpl#isLoggingEnabled <em>Logging Enabled</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EnsembleDefinitionImpl extends MinimalEObjectImpl.Container implements EnsembleDefinition {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMembership() <em>Membership</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMembership()
	 * @generated
	 * @ordered
	 */
	protected Condition membership;

	/**
	 * The cached value of the '{@link #getKnowledgeExchange() <em>Knowledge Exchange</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKnowledgeExchange()
	 * @generated
	 * @ordered
	 */
	protected Exchange knowledgeExchange;

	/**
	 * The cached value of the '{@link #getTriggers() <em>Triggers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTriggers()
	 * @generated
	 * @ordered
	 */
	protected EList<Trigger> triggers;

	/**
	 * The default value of the '{@link #getCommunicationBoundary() <em>Communication Boundary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommunicationBoundary()
	 * @generated
	 * @ordered
	 */
	protected static final CommunicationBoundaryPredicate COMMUNICATION_BOUNDARY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCommunicationBoundary() <em>Communication Boundary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommunicationBoundary()
	 * @generated
	 * @ordered
	 */
	protected CommunicationBoundaryPredicate communicationBoundary = COMMUNICATION_BOUNDARY_EDEFAULT;

	/**
	 * The default value of the '{@link #getPartitionedBy() <em>Partitioned By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartitionedBy()
	 * @generated
	 * @ordered
	 */
	protected static final String PARTITIONED_BY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPartitionedBy() <em>Partitioned By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartitionedBy()
	 * @generated
	 * @ordered
	 */
	protected String partitionedBy = PARTITIONED_BY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCoordinatorRole() <em>Coordinator Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoordinatorRole()
	 * @generated
	 * @ordered
	 */
	protected Class coordinatorRole;

	/**
	 * The cached value of the '{@link #getMemberRole() <em>Member Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemberRole()
	 * @generated
	 * @ordered
	 */
	protected Class memberRole;

	/**
	 * The default value of the '{@link #isLoggingEnabled() <em>Logging Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoggingEnabled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOGGING_ENABLED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLoggingEnabled() <em>Logging Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoggingEnabled()
	 * @generated
	 * @ordered
	 */
	protected boolean loggingEnabled = LOGGING_ENABLED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnsembleDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RuntimeMetadataPackage.Literals.ENSEMBLE_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimeMetadataPackage.ENSEMBLE_DEFINITION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Condition getMembership() {
		return membership;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMembership(Condition newMembership, NotificationChain msgs) {
		Condition oldMembership = membership;
		membership = newMembership;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RuntimeMetadataPackage.ENSEMBLE_DEFINITION__MEMBERSHIP, oldMembership, newMembership);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMembership(Condition newMembership) {
		if (newMembership != membership) {
			NotificationChain msgs = null;
			if (membership != null)
				msgs = ((InternalEObject)membership).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RuntimeMetadataPackage.ENSEMBLE_DEFINITION__MEMBERSHIP, null, msgs);
			if (newMembership != null)
				msgs = ((InternalEObject)newMembership).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RuntimeMetadataPackage.ENSEMBLE_DEFINITION__MEMBERSHIP, null, msgs);
			msgs = basicSetMembership(newMembership, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimeMetadataPackage.ENSEMBLE_DEFINITION__MEMBERSHIP, newMembership, newMembership));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Exchange getKnowledgeExchange() {
		return knowledgeExchange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKnowledgeExchange(Exchange newKnowledgeExchange, NotificationChain msgs) {
		Exchange oldKnowledgeExchange = knowledgeExchange;
		knowledgeExchange = newKnowledgeExchange;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RuntimeMetadataPackage.ENSEMBLE_DEFINITION__KNOWLEDGE_EXCHANGE, oldKnowledgeExchange, newKnowledgeExchange);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKnowledgeExchange(Exchange newKnowledgeExchange) {
		if (newKnowledgeExchange != knowledgeExchange) {
			NotificationChain msgs = null;
			if (knowledgeExchange != null)
				msgs = ((InternalEObject)knowledgeExchange).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RuntimeMetadataPackage.ENSEMBLE_DEFINITION__KNOWLEDGE_EXCHANGE, null, msgs);
			if (newKnowledgeExchange != null)
				msgs = ((InternalEObject)newKnowledgeExchange).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RuntimeMetadataPackage.ENSEMBLE_DEFINITION__KNOWLEDGE_EXCHANGE, null, msgs);
			msgs = basicSetKnowledgeExchange(newKnowledgeExchange, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimeMetadataPackage.ENSEMBLE_DEFINITION__KNOWLEDGE_EXCHANGE, newKnowledgeExchange, newKnowledgeExchange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Trigger> getTriggers() {
		if (triggers == null) {
			triggers = new EObjectContainmentEList<Trigger>(Trigger.class, this, RuntimeMetadataPackage.ENSEMBLE_DEFINITION__TRIGGERS);
		}
		return triggers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommunicationBoundaryPredicate getCommunicationBoundary() {
		return communicationBoundary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommunicationBoundary(CommunicationBoundaryPredicate newCommunicationBoundary) {
		CommunicationBoundaryPredicate oldCommunicationBoundary = communicationBoundary;
		communicationBoundary = newCommunicationBoundary;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimeMetadataPackage.ENSEMBLE_DEFINITION__COMMUNICATION_BOUNDARY, oldCommunicationBoundary, communicationBoundary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPartitionedBy() {
		return partitionedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPartitionedBy(String newPartitionedBy) {
		String oldPartitionedBy = partitionedBy;
		partitionedBy = newPartitionedBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimeMetadataPackage.ENSEMBLE_DEFINITION__PARTITIONED_BY, oldPartitionedBy, partitionedBy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Class getCoordinatorRole() {
		return coordinatorRole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCoordinatorRole(Class newCoordinatorRole) {
		Class oldCoordinatorRole = coordinatorRole;
		coordinatorRole = newCoordinatorRole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimeMetadataPackage.ENSEMBLE_DEFINITION__COORDINATOR_ROLE, oldCoordinatorRole, coordinatorRole));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Class getMemberRole() {
		return memberRole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemberRole(Class newMemberRole) {
		Class oldMemberRole = memberRole;
		memberRole = newMemberRole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimeMetadataPackage.ENSEMBLE_DEFINITION__MEMBER_ROLE, oldMemberRole, memberRole));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLoggingEnabled() {
		return loggingEnabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoggingEnabled(boolean newLoggingEnabled) {
		boolean oldLoggingEnabled = loggingEnabled;
		loggingEnabled = newLoggingEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimeMetadataPackage.ENSEMBLE_DEFINITION__LOGGING_ENABLED, oldLoggingEnabled, loggingEnabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__MEMBERSHIP:
				return basicSetMembership(null, msgs);
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__KNOWLEDGE_EXCHANGE:
				return basicSetKnowledgeExchange(null, msgs);
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__TRIGGERS:
				return ((InternalEList<?>)getTriggers()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__NAME:
				return getName();
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__MEMBERSHIP:
				return getMembership();
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__KNOWLEDGE_EXCHANGE:
				return getKnowledgeExchange();
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__TRIGGERS:
				return getTriggers();
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__COMMUNICATION_BOUNDARY:
				return getCommunicationBoundary();
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__PARTITIONED_BY:
				return getPartitionedBy();
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__COORDINATOR_ROLE:
				return getCoordinatorRole();
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__MEMBER_ROLE:
				return getMemberRole();
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__LOGGING_ENABLED:
				return isLoggingEnabled();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__NAME:
				setName((String)newValue);
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__MEMBERSHIP:
				setMembership((Condition)newValue);
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__KNOWLEDGE_EXCHANGE:
				setKnowledgeExchange((Exchange)newValue);
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__TRIGGERS:
				getTriggers().clear();
				getTriggers().addAll((Collection<? extends Trigger>)newValue);
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__COMMUNICATION_BOUNDARY:
				setCommunicationBoundary((CommunicationBoundaryPredicate)newValue);
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__PARTITIONED_BY:
				setPartitionedBy((String)newValue);
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__COORDINATOR_ROLE:
				setCoordinatorRole((Class)newValue);
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__MEMBER_ROLE:
				setMemberRole((Class)newValue);
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__LOGGING_ENABLED:
				setLoggingEnabled((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__MEMBERSHIP:
				setMembership((Condition)null);
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__KNOWLEDGE_EXCHANGE:
				setKnowledgeExchange((Exchange)null);
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__TRIGGERS:
				getTriggers().clear();
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__COMMUNICATION_BOUNDARY:
				setCommunicationBoundary(COMMUNICATION_BOUNDARY_EDEFAULT);
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__PARTITIONED_BY:
				setPartitionedBy(PARTITIONED_BY_EDEFAULT);
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__COORDINATOR_ROLE:
				setCoordinatorRole((Class)null);
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__MEMBER_ROLE:
				setMemberRole((Class)null);
				return;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__LOGGING_ENABLED:
				setLoggingEnabled(LOGGING_ENABLED_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__MEMBERSHIP:
				return membership != null;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__KNOWLEDGE_EXCHANGE:
				return knowledgeExchange != null;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__TRIGGERS:
				return triggers != null && !triggers.isEmpty();
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__COMMUNICATION_BOUNDARY:
				return COMMUNICATION_BOUNDARY_EDEFAULT == null ? communicationBoundary != null : !COMMUNICATION_BOUNDARY_EDEFAULT.equals(communicationBoundary);
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__PARTITIONED_BY:
				return PARTITIONED_BY_EDEFAULT == null ? partitionedBy != null : !PARTITIONED_BY_EDEFAULT.equals(partitionedBy);
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__COORDINATOR_ROLE:
				return coordinatorRole != null;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__MEMBER_ROLE:
				return memberRole != null;
			case RuntimeMetadataPackage.ENSEMBLE_DEFINITION__LOGGING_ENABLED:
				return loggingEnabled != LOGGING_ENABLED_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", communicationBoundary: ");
		result.append(communicationBoundary);
		result.append(", partitionedBy: ");
		result.append(partitionedBy);
		result.append(", coordinatorRole: ");
		result.append(coordinatorRole);
		result.append(", memberRole: ");
		result.append(memberRole);
		result.append(", loggingEnabled: ");
		result.append(loggingEnabled);
		result.append(')');
		return result.toString();
	}

} //EnsembleDefinitionImpl
