from django.conf.urls import url
from django.views.generic import TemplateView

from NeuroLogApp import views
from NeuroLogApp.views import DoctorView,PatientView

# rom NeuroLogApp.models import Patients
# from NeuroLogApp.models import Doctors
# from NeuroLogApp.models import GeneralPhysicalExams
# from NeuroLogApp.models import GenExamsAerialRecords
#
# from NeuroLogApp.models import ClinicalExams
# from NeuroLogApp.models import PainRecords
# from NeuroLogApp.models import HeadacheRecords
# from NeuroLogApp.models import NeurologicalExams
urlpatterns = [
    url(r'^$', views.index, name='index'),
    url(r'doctor/add/$', DoctorView.as_view(), name='doctor-add'),
    url(r'doctor_created/$', TemplateView.as_view(template_name='doctor_created.html'), name='doctor-add'),
    url(r'patient/add/$', PatientView.as_view(), name='patient-add'),
]
