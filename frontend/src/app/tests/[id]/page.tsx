/* eslint-disable @typescript-eslint/no-explicit-any */
/* eslint-disable @typescript-eslint/no-unused-vars */
'use client';

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { 
  Container, 
  Typography, 
  Paper, 
  Button, 
  TextField, 
  Box, 
  CircularProgress, 
  List, 
  ListItem, 
  Table, 
  TableBody, 
  TableCell, 
  TableHead, 
  TableRow
} from '@mui/material';

async function getTest(id: string) {
  try {
    const response = await axios.get(`/api/tests/${id}`);
    return response.data;
  } catch (error) {
    throw new Error('Failed to fetch test');
  }
}

async function updateTest(id: string, updatedTest: any) {
  try {
    const response = await axios.put(`/api/tests/${id}`, updatedTest);
    return response.data;
  } catch (error) {
    throw new Error('Failed to update test');
  }
}

async function runTest(id: string) {
  try {
    const response = await axios.post(`/api/tests/${id}/run`);
    return response.data;
  } catch (error) {
    throw new Error('Failed to run test');
  }
}

async function getReports(testId: string) {
  try {
    const response = await axios.get(`/api/reports?testId=${testId}`);
    return response.data;
  } catch (error) {
    throw new Error('Failed to fetch reports');
  }
}

export default function TestDetail({ params }: { params: { id: string } }) {
  const [test, setTest] = useState<any>(null);
  const [jsonInput, setJsonInput] = useState('');
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(true);
  const [isRunning, setIsRunning] = useState(false);
  const [reports, setReports] = useState<any[]>([]);

  useEffect(() => {
    const fetchTestAndReports = async () => {
      try {
        const [fetchedTest, fetchedReports] = await Promise.all([
          getTest(params.id),
          getReports(params.id)
        ]);
        setTest(fetchedTest);
        setJsonInput(JSON.stringify(fetchedTest, null, 2));
        setReports(fetchedReports);
      } catch (err) {
        setError('Failed to fetch data');
      } finally {
        setIsLoading(false);
      }
    };
    fetchTestAndReports();
  }, [params.id]);

  const handleJsonChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setJsonInput(event.target.value);
  };

  const handleEdit = async () => {
    try {
      const updatedTest = JSON.parse(jsonInput);
      const result = await updateTest(params.id, {...updatedTest, _id: undefined});
      setTest(result);
      setError('');
    } catch (err) {
      if (err instanceof SyntaxError) {
        setError('Invalid JSON format');
      } else {
        setError('Failed to update test');
      }
    }
  };

  const handleRun = async () => {
    setIsRunning(true);
    try {
      const result = await runTest(params.id);
      console.log('Test run result:', result);
      alert('Test run completed successfully!');
      // Refresh reports after running the test
      const newReports = await getReports(params.id);
      setReports(newReports);
    } catch (err) {
      setError('Failed to run test');
    } finally {
      setIsRunning(false);
    }
  };

  if (isLoading) {
    return <CircularProgress />;
  }

  if (error) {
    return <Typography variant="h6" color="error">{error}</Typography>;
  }

  if (!test) {
    return <Typography variant="h6">테스트를 찾을 수 없습니다.</Typography>;
  }

  return (
    <Container maxWidth="md">
      <Paper elevation={3} sx={{ p: 3, mt: 3 }}>
        <Typography variant="h4" gutterBottom>{test.name}</Typography>
        <Typography variant="body1">Type: {test.type}</Typography>
        <Typography variant="body1" gutterBottom>Scenarios: {test.scenarios?.length || 0}</Typography>
        
        <Box mt={3}>
          <Typography variant="h6" gutterBottom>Edit JSON</Typography>
          <TextField
            fullWidth
            multiline
            rows={20}
            variant="outlined"
            value={jsonInput}
            onChange={handleJsonChange}
            error={!!error}
            helperText={error}
          />
          <Box mt={2} display="flex" justifyContent="space-between">
            <Button variant="contained" color="primary" onClick={handleEdit}>
              Apply Changes
            </Button>
            <Button 
              variant="contained" 
              color="secondary" 
              onClick={handleRun}
              disabled={isRunning}
            >
              {isRunning ? 'Running...' : 'Run Test'}
            </Button>
          </Box>
        </Box>

        <Box mt={4}>
          <Typography variant="h5" gutterBottom>Reports</Typography>
          {reports.length > 0 ? (
            <List>
              {reports.map((report, index) => (
                <ListItem key={index}>
                  <Paper elevation={2} sx={{ p: 2, width: '100%' }}>
                    <Typography variant="h6">{report.name}</Typography>
                    <Typography>Status: {report.status}</Typography>
                    <Typography>Start Time: {new Date(report.startTime).toLocaleString()}</Typography>
                    {report.endTime && (
                      <Typography>End Time: {new Date(report.endTime).toLocaleString()}</Typography>
                    )}
                    <Typography variant="subtitle1" mt={2}>Summary</Typography>
                    <Table size="small">
                      <TableHead>
                        <TableRow>
                          <TableCell>Total Requests</TableCell>
                          <TableCell>Successful</TableCell>
                          <TableCell>Failed</TableCell>
                          <TableCell>Avg Response Time</TableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        <TableRow>
                          <TableCell>{report.summary?.totalRequests}</TableCell>
                          <TableCell>{report.summary?.successfulRequests}</TableCell>
                          <TableCell>{report.summary?.failedRequests}</TableCell>
                          <TableCell>{report.summary?.averageResponseTime.toFixed(2)} ms</TableCell>
                        </TableRow>
                      </TableBody>
                    </Table>
                  </Paper>
                </ListItem>
              ))}
            </List>
          ) : (
            <Typography>No reports available</Typography>
          )}
        </Box>
      </Paper>
    </Container>
  );
}