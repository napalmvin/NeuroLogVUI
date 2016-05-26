'''
Created on 11 apr. 2016 ?.
 Create the form classes.
@author: LOL
'''
import logging
from django.forms import ModelForm
from NeuroLogApp.models import Patients
from NeuroLogApp.models import Doctors
from NeuroLogApp.models import GeneralPhysicalExams
from NeuroLogApp.models import GenExamsAerialRecords

from NeuroLogApp.models import ClinicalExams
from NeuroLogApp.models import PainRecords
from NeuroLogApp.models import HeadacheRecords
from NeuroLogApp.models import NeurologicalExams

logger = logging.getLogger('forms')


# Create the form class.
class DoctorsForm(ModelForm):
    class Meta:
        model = Doctors
        fields = '__all__'

    def is_valid(self):
        logger.error('Calling form is valid')
        logger.error(self.errors.as_json())
        return super(DoctorsForm, self).is_valid()


class PatientsForm(ModelForm):
    class Meta:
        model = Patients
        fields = '__all__'


class GeneralPhysicalExamsForm(ModelForm):
    class Meta:
        model = GeneralPhysicalExams
        fields = '__all__'


class GenExamsAerialRecordsForm(ModelForm):
    class Meta:
        model = GenExamsAerialRecords
        fields = '__all__'


class ClinicalExamsForm(ModelForm):
    class Meta:
        model = ClinicalExams
        fields = '__all__'


class PainRecordsForm(ModelForm):
    class Meta:
        model = PainRecords
        fields = '__all__'


class  HeadacheRecordsForm(ModelForm):
    class Meta:
        model = HeadacheRecords
        fields = '__all__'


class NeurologicalExamsForm(ModelForm):
    class Meta:
        model = NeurologicalExams
        fields = '__all__'
