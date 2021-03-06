/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, The University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticweb.owlapi.debugging;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.util.CollectionFactory;

/** Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Mar-2007<br>
 * <br> */
@SuppressWarnings("unused")
public class DebuggerClassExpressionGenerator implements OWLAxiomVisitor {
    private final OWLDataFactory dataFactory;
    private OWLClassExpression desc;

    /** @param dataFactory
     *            factory to use */
    public DebuggerClassExpressionGenerator(OWLDataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }

    /** @return the class expression */
    public OWLClassExpression getDebuggerClassExpression() {
        return desc;
    }

    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        // A and not (B)
        OWLClassExpression complement = dataFactory.getOWLObjectComplementOf(axiom
                .getSuperClass());
        desc = dataFactory.getOWLObjectIntersectionOf(CollectionFactory.createSet(
                axiom.getSubClass(), complement));
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {}

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {}

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {}

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {}

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        OWLClassExpression sub = dataFactory.getOWLDataSomeValuesFrom(
                axiom.getProperty(), dataFactory.getTopDatatype());
        OWLAxiom ax = dataFactory.getOWLSubClassOfAxiom(sub, axiom.getDomain());
        ax.accept(this);
    }

    @SuppressWarnings("javadoc")
    public void visit(OWLImportsDeclaration axiom) {
        // Nothing to do
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        // prop some Thing subclassOf domain
        OWLClassExpression sub = dataFactory.getOWLObjectSomeValuesFrom(
                axiom.getProperty(), dataFactory.getOWLThing());
        OWLSubClassOfAxiom ax = dataFactory.getOWLSubClassOfAxiom(sub, axiom.getDomain());
        ax.accept(this);
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {}

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {}

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {}

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {}

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {}

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        // Thing subclassOf prop only Range
        OWLClassExpression sup = dataFactory.getOWLObjectAllValuesFrom(
                axiom.getProperty(), axiom.getRange());
        OWLSubClassOfAxiom ax = dataFactory.getOWLSubClassOfAxiom(
                dataFactory.getOWLThing(), sup);
        ax.accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {}

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {}

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        // subProp some {a} subClassOf supProp some {a}
        OWLIndividual ind = dataFactory.getOWLNamedIndividual(IRI
                .create("http://debugger.com#" + System.nanoTime()));
        OWLClassExpression sub = dataFactory.getOWLObjectHasValue(axiom.getSubProperty(),
                ind);
        OWLClassExpression sup = dataFactory.getOWLObjectHasValue(
                axiom.getSuperProperty(), ind);
        OWLAxiom ax = dataFactory.getOWLSubClassOfAxiom(sub, sup);
        ax.accept(this);
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {}

    @Override
    public void visit(OWLDeclarationAxiom axiom) {}

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {}

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {}

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {}

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {}

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {}

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        OWLClassExpression sub = dataFactory.getOWLObjectOneOf(Collections
                .singleton(axiom.getIndividual()));
        OWLAxiom ax = dataFactory.getOWLSubClassOfAxiom(sub, axiom.getClassExpression());
        ax.accept(this);
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        if (axiom.getClassExpressions().size() == 2
                && axiom.getClassExpressions().contains(dataFactory.getOWLNothing())) {
            for (OWLClassExpression c : axiom.getClassExpressions()) {
                if (!c.isOWLNothing()) {
                    desc = c;
                    return;
                }
            }
        }
        // (C and not D) or (not C and D)
        Set<OWLClassExpression> clses = axiom.getClassExpressions();
        Iterator<OWLClassExpression> it = clses.iterator();
        OWLClassExpression descC = it.next();
        OWLClassExpression notC = dataFactory.getOWLObjectComplementOf(descC);
        OWLClassExpression descD = it.next();
        OWLClassExpression notD = dataFactory.getOWLObjectComplementOf(descD);
        OWLObjectIntersectionOf left = dataFactory
                .getOWLObjectIntersectionOf(CollectionFactory.createSet(descC, notD));
        OWLObjectIntersectionOf right = dataFactory
                .getOWLObjectIntersectionOf(CollectionFactory.createSet(notC, descD));
        desc = dataFactory.getOWLObjectUnionOf(CollectionFactory.createSet(left, right));
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {}

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {}

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {}

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {}

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {}

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {}

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {}

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {}

    @Override
    public void visit(SWRLRule rule) {}

    @Override
    public void visit(OWLHasKeyAxiom axiom) {}

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {}

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {}

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {}

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {}
}
